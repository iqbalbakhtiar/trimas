package com.siriuserp.procurement.criteria;

import java.util.Date;

import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.filter.AbstractReportFilterCriteria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author AndresNodas
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public class PurchaseReportFilterCriteria extends AbstractReportFilterCriteria
{
	private static final long serialVersionUID = 2723769357935188581L;

	private Long supplier;
	private Long tax;
	private Long facility;
	private Long container;
	private Long product;
	private Long colourId;
	private Long fromProductId;

	private String productCode;
	private String sortBy;
	private String process;
	private String documentType;
	private String status;

	private Date dateFrom;
	private Date dateTo;

	private Month month;
	private Integer year;
}