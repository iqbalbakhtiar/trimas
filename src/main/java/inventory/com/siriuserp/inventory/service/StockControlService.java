/**
 * 
 */
package com.siriuserp.inventory.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.administration.util.LotHelper;
import com.siriuserp.inventory.dao.DataWarehouseDao;
import com.siriuserp.inventory.dao.ProductInOutAveragePriceDao;
import com.siriuserp.inventory.dm.Controllable;
import com.siriuserp.inventory.dm.Inventoriable;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.ProductInOutAveragePrice;
import com.siriuserp.inventory.dm.ProductTransaction;
import com.siriuserp.inventory.dm.Reservable;
import com.siriuserp.inventory.dm.StockControl;
import com.siriuserp.inventory.dm.StockControlType;
import com.siriuserp.inventory.dm.Stockable;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.DataAdditionException;
import com.siriuserp.sdk.exceptions.DataEditException;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
@Transactional(rollbackFor = Exception.class)
public class StockControlService 
{
	@Autowired
	private ProductInOutAveragePriceDao productInOutAveragePriceDao;

	@Autowired
	private DataWarehouseDao dataWarehouseDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception {
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("prices", productInOutAveragePriceDao.filter(QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	/**
	 * @param product target Product
	 * @param organization target organization
	 * @param quantity quantity
	 * @return Price created from AVERAGE process
	 */
	public BigDecimal average(Product product, Party organization, BigDecimal price, BigDecimal quantity) throws DataAdditionException, DataEditException
	{
		ProductInOutAveragePrice averagePrice = productInOutAveragePriceDao.load(product.getId(), organization.getId());
		if (averagePrice != null)
		{
			averagePrice.setPrice(
					(averagePrice.getPrice().multiply(averagePrice.getQuantity()).add(quantity.multiply(price))).divide(averagePrice.getQuantity().add(quantity), 23, RoundingMode.HALF_EVEN));
			averagePrice.setQuantity(averagePrice.getQuantity().add(quantity));

			dataWarehouseDao.update(averagePrice);
		} 
		else
		{
			averagePrice = new ProductInOutAveragePrice();
			averagePrice.setOrganization(organization);
			averagePrice.setPrice(price);
			averagePrice.setProduct(product);
			averagePrice.setQuantity(quantity);

			dataWarehouseDao.add(averagePrice);
		}

		return averagePrice.getPrice();
	}

	/**
	 * @param product target Product
	 * @param organization target organization
	 * @param quantity quantity
	 * @return T ProductTransaction created from FIFO/LIFO process
	 */
	public <T> T fifoLifo(Class<T> dataWarehouse, WarehouseTransactionItem item, Controllable controllable, Inventoriable inventory, Money money, BigDecimal quantity) throws Exception {
		ProductTransaction transaction = (ProductTransaction) Class.forName(dataWarehouse.getCanonicalName()).getDeclaredConstructor().newInstance();
		transaction.setControllable(controllable);
		transaction.setOriginItem(item);
		
		if(inventory.getLot() != null)
		{
			transaction.setInfo(inventory.getLot().getInfo());
			transaction.setCode(inventory.getLot().getCode());
			transaction.setSerial(inventory.getLot().getSerial());
		}

		transaction.setCurrency(dataWarehouseDao.load(Currency.class, money.getCurrency().getId()));
		transaction.setRate(money.getRate());
		transaction.setPrice(money.getAmount());
		
		transaction.setDate(controllable.getDate());
		transaction.setOrganization(inventory.getOrganization());
		transaction.setContainer(inventory.getContainer());
		transaction.setProduct(inventory.getProduct());
		transaction.setQuantity(quantity);
		transaction.setReceipted(transaction.getQuantity());
		transaction.setCreatedBy(UserHelper.activePerson());
		transaction.setCreatedDate(DateHelper.now());

		return (T) transaction;
	}
	
	public Set<Map<String, BigDecimal>> price(Class warehouse, Inventoriable inventory, StockControlType stockControlType) throws Exception 
	{
		List<ProductTransaction> fifo = dataWarehouseDao.loadAll(warehouse, inventory.getOrganization().getId(), inventory.getSourceContainer().getId(), 
				inventory.getProduct().getId(), inventory instanceof Reservable ? null : inventory.getLot(), inventory.getDate(), stockControlType);
		
		BigDecimal buffer = inventory.getQuantity().abs();
		
		Set<Map<String, BigDecimal>> list = new HashSet<Map<String,BigDecimal>>();
		
		for (ProductTransaction transaction : fifo)
		{
			Map<String, BigDecimal> map = new FastMap<String, BigDecimal>();
 			
			if (buffer.compareTo(BigDecimal.ZERO) <= 0)
				break;
			
			map.put("cogs", transaction.getPrice());
			map.put("rate", transaction.getRate());
	
			list.add(map);
		}
		
		return list;
	}

	/**
	 * @param product target Product
	 * @param organization target organization
	 * @param quantity quantity
	 * @param stockControlType StockControlType(FIFO/LIFO/AVERAGE)
	 * @return List<StockControll> stock controll created from FIFO/LIFO process
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Set<StockControl> get(Class warehouse, Inventoriable inventory, Stockable stockable, StockControlType stockControlType) throws Exception {
		Set<StockControl> list = new HashSet<StockControl>();

		//Stock data for FIFO/LIFO
		if (!stockControlType.equals(StockControlType.AVERAGE))
		{
			List<ProductTransaction> fifo = dataWarehouseDao.loadAll(warehouse, inventory.getOrganization().getId(), inventory.getSourceContainer().getId(), 
				inventory.getProduct().getId(), inventory instanceof Reservable ? null : inventory.getLot(), inventory.getDate(), stockControlType);

			if (!fifo.isEmpty())
			{
				BigDecimal buffer = inventory.getQuantity().abs();

				for (ProductTransaction transaction : fifo)
				{
					if (buffer.compareTo(BigDecimal.ZERO) <= 0)
						break;

					StockControl control = new StockControl();	
					control.setCode(transaction.getCode());
					control.setSerial(transaction.getSerial());
					control.setInfo(transaction.getInfo());

					control.setPrice(transaction.getPrice());
					control.setRate(transaction.getRate());
					control.setCurrency(transaction.getCurrency());
					control.setSourceItem(transaction.getOriginItem());
					control.setControllable(transaction.getControllable());
					control.setStockable(stockable);
					control.setDestinationItem(stockable.getWarehouseTransactionItem());
			
					if (transaction.getQuantity().compareTo(buffer) >= 0)
					{
						control.setQuantity(buffer);

						transaction.setQuantity(transaction.getQuantity().subtract(buffer));

						buffer = BigDecimal.ZERO;
					} 
					else
					{
						control.setQuantity(transaction.getQuantity());

						buffer = buffer.subtract(transaction.getQuantity());

						transaction.setQuantity(BigDecimal.ZERO);
					}

					control.setBuffer(control.getQuantity());
					
					//For Convertion Reserved Global Stock to Detail Stock
					if(inventory instanceof Reservable)
					{
						Money money = new Money();
						money.setCurrency(transaction.getCurrency());
						money.setRate(transaction.getRate());
						money.setAmount(transaction.getPrice());

						dataWarehouseDao.add(fifoLifo(warehouse, stockable.getWarehouseTransactionItem(), transaction.getControllable(), inventory, money, inventory.getQuantity().abs()));
					}

					dataWarehouseDao.update(transaction);

					list.add(control);
				}

				if (buffer.compareTo(BigDecimal.ZERO) > 0)
					throw new ServiceException("Quantity " + inventory.getProduct().getName() + " [" + inventory.getSourceContainer().getName() + "] "
						+ "["+SiriusValidator.getEmptyStringParam(inventory.getLot().getCode())+"] ["+SiriusValidator.getEmptyStringParam(inventory.getLot().getInfo())+"] "
							+ "["+SiriusValidator.getEmptyStringParam(inventory.getLot().getSerial())+"]"
								+ "requested is bigger then available quantity on Stock Controll !");
			} else
				throw new ServiceException("Stock " + inventory.getProduct().getName() + " [" + inventory.getSourceContainer().getName() + "] "
					+ "["+SiriusValidator.getEmptyStringParam(inventory.getLot().getCode())+"] ["+SiriusValidator.getEmptyStringParam(inventory.getLot().getInfo())+"]"
						+ "["+SiriusValidator.getEmptyStringParam(inventory.getLot().getSerial())+"] "
							+ "unsync with On Hand Quantity, please recheck again !");
		} else
		{
			ProductInOutAveragePrice averagePrice = productInOutAveragePriceDao.load(inventory.getProduct().getId(), inventory.getOrganization().getId());

			if (averagePrice != null)
			{
				averagePrice.setQuantity(averagePrice.getQuantity().subtract(inventory.getQuantity()));

				productInOutAveragePriceDao.update(averagePrice);

				StockControl control = new StockControl();
				control.setPrice(averagePrice.getPrice());

				list.add(control);
			}
		}

		return list;
	}
	
	/*
	 * Stock control was simulated for ProductInOutTransaction
	 */
	public <T> Set<T> buffer(Class<T> dataWarehouse, Set<StockControl> controls, Controllable controllable) throws Exception {
		Set<T> trans = new HashSet<T>();

		BigDecimal buffer = controllable.getQuantity();
		BigDecimal in = BigDecimal.ZERO;

		for (StockControl control : controls)
		{
			if (SiriusValidator.gz(control.getBuffer()) && control.getStockable().getSelf().compareTo("GoodsIssueItem") == 0 &&
				(control.getLot() == null || LotHelper.getCompare(control.getLot()).compareTo(LotHelper.getCompare(controllable.getOriginLot())) == 0))
			{
				if (buffer.compareTo(BigDecimal.ZERO) <= 0)
					break;

				if (control.getBuffer().compareTo(buffer) >= 0)
				{
					in = buffer;
					control.setBuffer(control.getBuffer().subtract(buffer));

					buffer = BigDecimal.ZERO;
				} 
				else
				{
					in = control.getBuffer();
					buffer = buffer.subtract(control.getBuffer());

					control.setBuffer(BigDecimal.ZERO);
				}
				
				dataWarehouseDao.update(control);

				if (in.compareTo(BigDecimal.ZERO) > 0) {
					controllable.setStockable(control.getStockable());
					
					trans.add(fifoLifo(dataWarehouse, control.getSourceItem(), controllable, controllable, control.getMoney(), in));
				}
			}
		}
		
		if (buffer.compareTo(BigDecimal.ZERO) > 0)
			throw new ServiceException("Buffer Quantity " + controllable.getProduct().getName() + "[" + controllable.getSourceContainer().getName() + "] requested is bigger then available quantity on Stock Controll !");

		return trans;
	}
	
	public void unbuffer(Set<StockControl> controls, Controllable controllable) throws DataEditException
	{
		BigDecimal buffer = controllable.getQuantity();
		
		for (StockControl control : controls)
			if (control.getStockable().getSelf().compareTo("GoodsIssueItem") == 0 && 
				(control.getLot() == null || LotHelper.getKey(control.getLot()).compareTo(LotHelper.getKey(controllable.getOriginLot())) == 0))
			{
				if (buffer.compareTo(BigDecimal.ZERO) <= 0)
					break;

				if (control.getQuantity().compareTo(buffer) >= 0)
				{
					control.setBuffer(control.getBuffer().add(buffer));

					buffer = BigDecimal.ZERO;
				} 
				else
				{
					buffer = buffer.subtract(control.getQuantity());

					control.setBuffer(control.getQuantity());
				}
				
				dataWarehouseDao.update(control);
			}
	}
}
