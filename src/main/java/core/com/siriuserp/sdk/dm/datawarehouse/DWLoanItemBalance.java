/**
 * 
 */
package com.siriuserp.sdk.dm.datawarehouse;

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

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Entity
@Table(name="dw_loan_item_balance")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class DWLoanItemBalance extends Model
{
	private static final long serialVersionUID = 7368940599227018010L;

	@Column(name="reference_id")
    private Long referenceId;
	    
    @Column(name="reference_code")
    private String referenceCode;
	    
    @Column(name="reference_type")
    private String referenceType;
	    
    @Column(name="date")
    private Date date;
	    
    @Column(name="quantity_in")
    private BigDecimal in = BigDecimal.ZERO;

    @Column(name="quantity_out")
    private BigDecimal out = BigDecimal.ZERO;
	    
    @Column(name="cogs")
    private BigDecimal cogs = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name="month")
    private Month month;

    @Column(name="year")
    private Integer year;

    @Column(name="product_id")
    private Long productId;

    @Column(name="product_code")
    private String productCode;

    @Column(name="product_name")
    private String productName;

    @Column(name="product_uom")
    private String uom;

    @Column(name="product_category_id")
    private Long productCategoryId;

    @Column(name="product_category_code")
    private String productCategoryCode;

    @Column(name="product_category_name")
    private String productCategoryName;

    @Column(name="organization_id")
    private Long organizationId;

    @Column(name="organization_code")
    private String organizationCode;

    @Column(name="organization_name")
    private String organizationName;
	    
    @Column(name="customer_id")
    private Long customerId;

    @Column(name="customer_code")
    private String customerCode;

    @Column(name="customer_name")
    private String customerName;
	    
    @Column(name="container_id")
    private Long containerId;

    @Column(name="container_code")
    private String containerCode;

    @Column(name="container_name")
    private String containerName;

    @Column(name="facility_id")
    private Long facilityId;

    @Column(name="facility_code")
    private String facilityCode;

    @Column(name="facility_name")
    private String facilityName;

    @Column(name="grid_id")
    private Long gridId;

    @Column(name="grid_code")
    private String gridCode;

    @Column(name="grid_name")
    private String gridName;

    public DWLoanItemBalance() {}
	    
    public Long getReferenceId()
    {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) 
	{
		this.referenceId = referenceId;
	}

	public String getReferenceCode()
	{
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) 
	{
		this.referenceCode = referenceCode;
	}

	public String getReferenceType() 
	{
		return referenceType;
	}

	public void setReferenceType(String referenceType) 
	{
		this.referenceType = referenceType;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date) 
	{
		this.date = date;
	}

	public BigDecimal getIn() 
	{
		return in;
	}

	public void setIn(BigDecimal in) 
	{
		this.in = in;
	}

	public BigDecimal getOut() 
	{
		return out;
	}

	public void setOut(BigDecimal out) 
	{
		this.out = out;
	}

	public BigDecimal getCogs() 
	{
		return cogs;
	}

	public void setCogs(BigDecimal cogs) 
	{
		this.cogs = cogs;
	}

	public Month getMonth() 
	{
		return month;
	}

	public void setMonth(Month month)
	{
		this.month = month;
	}

	public Integer getYear() 
	{
		return year;
	}

	public void setYear(Integer year) 
	{
		this.year = year;
	}

	public Long getProductId() 
	{
		return productId;
	}

	public void setProductId(Long productId) 
	{
		this.productId = productId;
	}

	public String getProductCode() 
	{
		return productCode;
	}

	public void setProductCode(String productCode) 
	{
		this.productCode = productCode;
	}

	public String getProductName() 
	{
		return productName;
	}

	public void setProductName(String productName) 
	{
		this.productName = productName;
	}

	public String getUom() 
	{
		return uom;
	}

	public void setUom(String uom) 
	{
		this.uom = uom;
	}

	public Long getProductCategoryId() 
	{
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) 
	{
		this.productCategoryId = productCategoryId;
	}

	public String getProductCategoryCode() 
	{
		return productCategoryCode;
	}

	public void setProductCategoryCode(String productCategoryCode) 
	{
		this.productCategoryCode = productCategoryCode;
	}

	public String getProductCategoryName() 
	{
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) 
	{
		this.productCategoryName = productCategoryName;
	}

	public Long getOrganizationId() 
	{
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) 
	{
		this.organizationId = organizationId;
	}

	public String getOrganizationCode() 
	{
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) 
	{
		this.organizationCode = organizationCode;
	}

	public String getOrganizationName() 
	{
		return organizationName;
	}

	public void setOrganizationName(String organizationName) 
	{
		this.organizationName = organizationName;
	}
		
	public Long getCustomerId() 
	{
		return customerId;
	}

	public void setCustomerId(Long customerId) 
	{
		this.customerId = customerId;
	}

	public String getCustomerCode() 
	{
		return customerCode;
	}

	public void setCustomerCode(String customerCode) 
	{
		this.customerCode = customerCode;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public Long getContainerId() 
	{
		return containerId;
	}

	public void setContainerId(Long containerId) 
	{
		this.containerId = containerId;
	}

	public String getContainerCode() 
	{
		return containerCode;
	}

	public void setContainerCode(String containerCode) 
	{
		this.containerCode = containerCode;
	}

	public String getContainerName()
	{
		return containerName;
	}

	public void setContainerName(String containerName) 
	{
		this.containerName = containerName;
	}

	public Long getFacilityId() 
	{
		return facilityId;
	}

	public void setFacilityId(Long facilityId) 
	{
		this.facilityId = facilityId;
	}

	public String getFacilityCode() 
	{
		return facilityCode;
	}

	public void setFacilityCode(String facilityCode) 
	{
		this.facilityCode = facilityCode;
	}

	public String getFacilityName() 
	{
		return facilityName;
	}

	public void setFacilityName(String facilityName) 
	{
		this.facilityName = facilityName;
	}

	public Long getGridId() 
	{
		return gridId;
	}

	public void setGridId(Long gridId) 
	{
		this.gridId = gridId;
	}

	public String getGridCode() 
	{
		return gridCode;
	}

	public void setGridCode(String gridCode) 
	{
		this.gridCode = gridCode;
	}

	public String getGridName() 
	{
		return gridName;
	}

	public void setGridName(String gridName) 
	{
		this.gridName = gridName;
	}

	@Override
	public String getAuditCode()
	{
		return null;
	}
}
