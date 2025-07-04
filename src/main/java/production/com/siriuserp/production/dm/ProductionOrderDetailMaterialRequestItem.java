package com.siriuserp.production.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.Reservable;
import com.siriuserp.inventory.dm.WarehouseReferenceItem;
import com.siriuserp.inventory.dm.WarehouseTransaction;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Money;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "production_order_detail_material_request_item")
public class ProductionOrderDetailMaterialRequestItem extends WarehouseReferenceItem implements Reservable
{
	private static final long serialVersionUID = 7005809830729147673L;

	@Column(name = "quantity")
    private BigDecimal quantity = BigDecimal.ZERO;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_production_order_detail_material_request")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
	private ProductionOrderDetailMaterialRequest productionOrderDetailMaterialRequest;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_container_source")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Container sourceContainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_container_destination")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Container destinationContainer;
	
    @Override
    public Money getMoney()
    {
        Money money = new Money();
        money.setCurrency(Currency.newInstance("1"));
        money.setExchangeType(ExchangeType.MIDDLE);

        return money;
    }
    
    @Override
	public Grid getGrid() {
		return null;
	}
    
    @Override
    public Grid getSourceGrid()
    {
        return getSourceContainer().getGrid();
    }

    @Override
    public Grid getDestinationGrid()
    {
        return getDestinationContainer().getGrid();
    }

    @Override
    public Long getReferenceId()
    {
        return getProductionOrderDetailMaterialRequest().getId();
    }

    @Override
    public String getReferenceCode()
    {
        return getProductionOrderDetailMaterialRequest().getCode();
    }

    @Override
    public String getRefFrom()
    {
        return getProductionOrderDetailMaterialRequest().getSource().getName();
    }

    @Override
    public String getRefTo()
    {
        return getProductionOrderDetailMaterialRequest().getDestination().getName();
    }
    
	@Override
    public WarehouseTransaction getWarehouseTransaction() {
        return getProductionOrderDetailMaterialRequest();
    }

    @Override
    public WarehouseTransactionSource getTransactionSource() {
        return WarehouseTransactionSource.PRODUCTION_ORDER_DETAIL_MATERIAL_REQUEST;
    }
}
