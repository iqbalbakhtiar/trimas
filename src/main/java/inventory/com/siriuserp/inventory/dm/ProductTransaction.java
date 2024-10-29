/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Party;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */
public interface ProductTransaction 
{	
	public void setCreatedBy(Party createdBy);
	public void setCreatedDate(Timestamp createdDate);
    public void setQuantity(BigDecimal quantity);
    public void setReceipted(BigDecimal receipted);
	public void setPrice(BigDecimal buyingExchange);
	public void setRate(BigDecimal rate);
    public void setCurrency(Currency buyingCurrency);
    public void setProduct(Product product);
    public void setOrganization(Party organization);
    public void setContainer(Container container);
	public void setDate(Date date);
	public void setOriginItem(WarehouseTransactionItem item);
	public void setControllable(Controllable controllable);
	
	public void setInfo(String info);
	public void setCode(String code);
	public void setSerial(String serial);
	
	public BigDecimal getQuantity();
	public BigDecimal getPrice();
	public BigDecimal getRate();
	public Currency getCurrency();
	public WarehouseTransactionItem getOriginItem();
	public Controllable getControllable();
	
	public String getInfo();
	public String getCode();
	public String getSerial(); 
	
	public Date getDate();
}
