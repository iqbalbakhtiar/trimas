/**
 * 
 */
package com.siriuserp.accounting.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "memo_item")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MemoItem extends Model
{
	private static final long serialVersionUID = -540438220713782007L;

	@Embedded
	protected Lot lot = new Lot();

	protected BigDecimal amount = BigDecimal.ZERO;

	@Column(name = "buffer")
	protected BigDecimal buffer = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Product product;

	@Override
	public String getAuditCode()
	{
		return getId() + " ";
	}
}
