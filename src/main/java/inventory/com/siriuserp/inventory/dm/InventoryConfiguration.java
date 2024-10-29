/**
 * 
 */
package com.siriuserp.inventory.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;

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
@Table(name = "inventory_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryConfiguration extends Model
{
	private static final long serialVersionUID = 7555172840902473717L;

	@Column(name = "product_code_auto_generated")
	@Type(type = "yes_no")
	private boolean productCodeAuto = true;
	    
	@Enumerated(EnumType.STRING)
	@Column(name = "inventory_transaction_type")
	private StockControlType transactionType = StockControlType.FIFO;
	
	@Override
	public String getAuditCode() {
		return getId() + "";
	}
}
