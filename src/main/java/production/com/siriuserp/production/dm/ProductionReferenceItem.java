package com.siriuserp.production.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
@Entity
@Table(name = "production_reference_item")
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public abstract class ProductionReferenceItem extends Model
{
	private static final long serialVersionUID = -9065582879026307890L;

	@Column(name = "reference_code")
	protected String referenceCode;
	
	@Column(name = "quantity")
	protected BigDecimal quantity = BigDecimal.ZERO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Product product;
}
