/**
 * Mar 5, 2010 2:48:16 PM
 * com.siriuserp.sdk.dm
 * InventoryItemBalanceDetail.java
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Month;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dw_inventory_item_balance_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DWInventoryItemBalanceDetail extends Model implements DataWarehouseDetail
{
	private static final long serialVersionUID = 5080110314259337933L;

	@Column(name = "reference_id")
	private Long referenceId;

	@Column(name = "reference_code")
	private String referenceCode;

	@Column(name = "reference_parent_code")
	private String referenceParentCode;

	@Column(name = "reference_type")
	private String referenceType;

	@Column(name = "reference_uri")
	private String referenceUri;

	@Column(name = "warehouse_id")
	private Long warehouseId;

	@Column(name = "warehouse_code")
	private String warehouseCode;

	@Column(name = "warehouse_type")
	private String warehouseType;

	@Column(name = "warehouse_uri")
	private String warehouseUri;

	@Column(name = "date")
	private Date date;

	@Column(name = "quantity_in")
	private BigDecimal in = BigDecimal.ZERO;

	@Column(name = "quantity_out")
	private BigDecimal out = BigDecimal.ZERO;

	@Column(name = "cogs")
	private BigDecimal cogs = BigDecimal.ZERO;

	@Enumerated(EnumType.STRING)
	@Column(name = "month")
	private Month month;

	@Column(name = "year")
	private Integer year;

	@Column(name = "serial_no")
	private String serial;
	
	@Column(name = "lot_code")
	private String lotCode;

	@Column(name = "lot_info")
	private String lotInfo;
	
	@Column(name = "tag")
	private String tag;
	
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "product_code")
	private String productCode;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_uom")
	private String uom;

	@Column(name = "product_category_id")
	private Long productCategoryId;

	@Column(name = "product_category_code")
	private String productCategoryCode;

	@Column(name = "product_category_name")
	private String productCategoryName;

	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "organization_code")
	private String organizationCode;

	@Column(name = "organization_name")
	private String organizationName;

	@Column(name = "container_id")
	private Long containerId;

	@Column(name = "container_code")
	private String containerCode;

	@Column(name = "container_name")
	private String containerName;

	@Column(name = "facility_id")
	private Long facilityId;

	@Column(name = "facility_code")
	private String facilityCode;

	@Column(name = "facility_name")
	private String facilityName;

	@Column(name = "grid_id")
	private Long gridId;

	@Column(name = "grid_code")
	private String gridCode;

	@Column(name = "grid_name")
	private String gridName;

	@Column(name = "note")
	private String note;

	public String getType()
	{
		return getWarehouseType() == null ? getReferenceType().toUpperCase() : getWarehouseType() ;
	}

	@Override
	public String getAuditCode()
	{
		return null;
	}

}
