package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.filter.AbstractReportFilterCriteria;
import javolution.util.FastList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InventoryLedgerFilterCriteria extends AbstractReportFilterCriteria
{
	private static final long serialVersionUID = -8831530257792290868L;

	private Month month;
	private Integer year;

	private Date dateFrom;
	private Date dateTo;

	private Long facility;
	private Long container;
	private Long product;
	private Long periode;
	private Long productCategory;
	private Long supplierId;
	private Long supplier;
	private Long tax;
	private Long colourId;

	private String lotCode;
	private String itemName;
	private String customer;
	private String reference;
	private String sortBy;
	private String type;
	private String enabled;
	private String process;
	private String department;

	private boolean priced = false;
	private boolean reserved = false;
	private boolean transfer = false;
	private boolean onhand = false;

	private Boolean showLot = true;
	private Boolean showBale = false;
	private Boolean status;

	private List<Long> containers = new FastList<Long>();
}
