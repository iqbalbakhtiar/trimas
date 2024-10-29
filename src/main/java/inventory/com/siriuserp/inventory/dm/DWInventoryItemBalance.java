/**
 * Mar 5, 2010 2:28:52 PM
 * com.siriuserp.sdk.dm
 * InventoryItemBalance.java
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
@Table(name = "dw_inventory_item_balance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DWInventoryItemBalance extends Model implements DataWarehouse
{
    private static final long serialVersionUID = -2379320463059582326L;

    @Column(name = "quantity_in")
    private BigDecimal in = BigDecimal.ZERO;

    @Column(name = "quantity_out")
    private BigDecimal out = BigDecimal.ZERO;
   
    @Column(name = "date")
    private Date date;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private Month month;

    @Column(name = "year")
    private Integer year;

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
    
    @Column(name = "moving_id")
    private Long movingId;

    @Column(name = "moving_code")
    private String movingCode;

    @Column(name = "moving_name")
    private String movingName;

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

    @Override
    public String getAuditCode()
    {
        return null;
    }
}
