/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.sdk.dm.Month;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */
public interface DataWarehouse 
{
	public BigDecimal getIn();
	
	public BigDecimal getOut();
	
    public void setDate(Date date);

    public void setIn(BigDecimal in);

    public void setOut(BigDecimal out);

    public void setMonth(Month month);

    public void setYear(Integer year);

    public void setProductId(Long productId);

    public void setProductCode(String productCode);

    public void setProductName(String productName);

    public void setUom(String uom);

    public void setProductCategoryId(Long productCategoryId);

    public void setProductCategoryCode(String productCategoryCode);

    public void setProductCategoryName(String productCategoryName);

    public void setOrganizationId(Long organizationId);

    public void setOrganizationCode(String organizationCode);

    public void setOrganizationName(String organizationName);

    public void setContainerId(Long containerId);

    public void setContainerCode(String containerCode);

    public void setContainerName(String containerName);
    
    public void setMovingId(Long containerId);

    public void setMovingCode(String containerCode);

    public void setMovingName(String containerName);

    public void setFacilityId(Long facilityId);

    public void setFacilityCode(String facilityCode);

    public void setFacilityName(String facilityName);

    public void setGridId(Long gridId);

    public void setGridCode(String gridCode);

    public void setGridName(String gridName);
}
