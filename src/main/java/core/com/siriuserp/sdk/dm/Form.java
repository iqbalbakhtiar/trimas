/**
 * 
 */
package com.siriuserp.sdk.dm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.siriuserp.inventory.dm.Product;

import javolution.util.FastList;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 *
 */

@Getter
@Setter
public class Form implements SiriusForm
{
	private static final long serialVersionUID = 2074583835581144873L;

	private Date date;
	private Date approvalDate;
	private Date validFrom;
	private Date validTo;
	private Date createdDate;
	private Date updatedDate;

	private String code;
	private String name;
	private String legend;
	private String note;
	private String reason;
	private String requester;
	private String contactPerson;
	private String remark;
	private String url;
	private String uri;

	private Long no;
	private Long syncId;
	private Long referenceId;

	private Integer term;

	private Party organization;
	private Facility facility;
	private Party person;
	private Party party;
	private Party owner;
	private Party customer;
	private Party supplier;
	private Party requisitioner;
	private Party approver;
	private Party forwardTo;
	private Party completedBy;
	private Party createdBy;
	private Party updatedBy;

	private PostalAddress postalAddress;
	private Product product;

	private Tax tax;
	private Tax extTax1;
	private Tax extTax2;

	private Currency currency;
	private Exchange exchange;

	private ExchangeType exchangeType;
	private TableType tableType;
	private ApprovalDecisionStatus approvalDecisionStatus;

	private BigDecimal amount = BigDecimal.ZERO;
	private BigDecimal discount = BigDecimal.ZERO;
	private BigDecimal rounding = BigDecimal.ZERO;
	private BigDecimal deliveryCost = BigDecimal.ZERO;
	private BigDecimal rate = BigDecimal.ONE;
	private BigDecimal extTax1Rate = BigDecimal.ZERO;
	private BigDecimal total = BigDecimal.ZERO;
	private BigDecimal unitCost = BigDecimal.ZERO;

	private boolean base = true;
	private boolean enabled = true;
	private boolean serial = false;

	private MultipartFile file;

	private final List<Item> items = new FastList<Item>();
}
