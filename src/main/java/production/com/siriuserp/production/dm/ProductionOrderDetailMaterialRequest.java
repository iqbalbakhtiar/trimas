package com.siriuserp.production.dm;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.WarehouseReferenceItem;
import com.siriuserp.inventory.dm.Warehouseable;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tax;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "production_order_detail_material_request")
public class ProductionOrderDetailMaterialRequest extends Model implements Warehouseable
{
	private static final long serialVersionUID = 8090035718951338814L;

	@Column(name = "code")
	private String code;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "note")
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_machine")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Machine machine;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility_source")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility_destination")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility destination;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_container")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Container container;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_production_order_detail")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private ProductionOrderDetail productionOrderDetail;
    
    @OneToMany(mappedBy = "productionOrderDetailMaterialRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    @OrderBy("product.id ASC")
    private Set<ProductionOrderDetailMaterialRequestItem> items = new FastSet<ProductionOrderDetailMaterialRequestItem>();
	
	@Override
	public Tax getTax() {
		return Tax.newInstance("1", "Exempt");
	}

	@Override
	public Currency getCurrency() {
		return Currency.newInstance("1");
	}

	@Override
	public Party getParty() {
		return getProductionOrderDetail().getProductionOrder().getOrganization();
	}
	
	@Override
	public Party getOrganization() {
		return getProductionOrderDetail().getProductionOrder().getOrganization();
	}

	@Override
	public String getRef() {
		return "";
	}

	@Override
	public Set<? extends WarehouseReferenceItem> getReceiptables() {
		return getItems();
	}

	@Override
	public Set<GoodsReceipt> getReceipts() {
		FastSet<GoodsReceipt> receipts = new FastSet<GoodsReceipt>();

        for (ProductionOrderDetailMaterialRequestItem detail : getItems())
            receipts.addAll(detail.getReceipts());

        return receipts;
	}
	
	@Override
	public Set<? extends WarehouseReferenceItem> getIssueables() {
		return getItems();
	}

	@Override
	public Set<GoodsIssue> getIssueds() {
		FastSet<GoodsIssue> issueds = new FastSet<GoodsIssue>();

        for (ProductionOrderDetailMaterialRequestItem detail : getItems())
            issueds.addAll(detail.getIssueds());

        return issueds;
	}
	
	@Override
    public String getSelf() {
        return "Production Order Detail Material Request";
    }
	
	@Override
	public String getAuditCode() {
		return this.id + "," + this.code;
	}
}
