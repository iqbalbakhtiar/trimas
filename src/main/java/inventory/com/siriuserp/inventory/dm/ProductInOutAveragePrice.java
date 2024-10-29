/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.utility.DateHelper;

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
@Table(name = "product_in_out_average_price")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductInOutAveragePrice extends Model implements ProductTransaction
{
	private static final long serialVersionUID = 6535294657197206534L;
	
	@Column(name = "quantity")
	private BigDecimal quantity;

	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "rate")
	private BigDecimal rate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@Override
	public String getAuditCode() {
		return null;
	}

	@Override
	public void setReceipted(BigDecimal receipted) {
	}

	@Override
	public void setCurrency(Currency buyingCurrency) {
	}

	@Override
	public void setContainer(Container container) {
	}

	@Override
	public void setDate(Date date) {
	}

	@Override
	public void setOriginItem(WarehouseTransactionItem item) {
	}

	@Override
	public void setControllable(Controllable controllable) {
	}

	@Override
	public void setInfo(String info) {
	}

	@Override
	public void setCode(String code) {
	}

	@Override
	public void setSerial(String serial) {
	}

	@Override
	public Currency getCurrency() {
		return null;
	}

	@Override
	public WarehouseTransactionItem getOriginItem() {
		return null;
	}

	@Override
	public Controllable getControllable() {
		return null;
	}

	@Override
	public String getInfo() {
		return null;
	}

	@Override
	public String getCode() {
		return null;
	}

	@Override
	public String getSerial() {
		return null;
	}

	@Override
	public Date getDate() {
		return DateHelper.now();
	}
}
