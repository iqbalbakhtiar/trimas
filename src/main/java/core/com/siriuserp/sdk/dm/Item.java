/**
 * 
 */
package com.siriuserp.sdk.dm;

import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.production.dm.CostCenter;
import com.siriuserp.production.dm.MaterialType;
import com.siriuserp.sales.dm.DeliveryOrderItemType;
import com.siriuserp.sales.dm.DeliveryOrderReferenceItem;
import com.siriuserp.sales.dm.SalesType;
import org.springframework.web.multipart.MultipartFile;

import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.accountpayable.dm.InvoiceVerificationItem;
import com.siriuserp.accountreceivable.dm.BillingReferenceItem;
import com.siriuserp.accountreceivable.dm.WriteOffType;
import com.siriuserp.administration.dm.Geographic;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.UnitOfMeasure;

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
public class Item extends SiriusItem
{
	private static final long serialVersionUID = 3584646505653801611L;

	private BigDecimal quantity = BigDecimal.ZERO;
	private BigDecimal quantityReal = BigDecimal.ZERO;
	private BigDecimal quantityCone = BigDecimal.ZERO;
	private BigDecimal assigned = BigDecimal.ZERO;
	private BigDecimal price = BigDecimal.ZERO;
	private BigDecimal rate = BigDecimal.ZERO;
	private BigDecimal discount = BigDecimal.ZERO;
	private BigDecimal discountPercent = BigDecimal.ZERO;
	private BigDecimal weight = BigDecimal.ZERO;
	private BigDecimal commission = BigDecimal.ZERO;
	private BigDecimal receipted = BigDecimal.ZERO;
	private BigDecimal adjustment = BigDecimal.ZERO;
	private BigDecimal amount = BigDecimal.ZERO;
	private BigDecimal resend = BigDecimal.ZERO;
	private BigDecimal returned = BigDecimal.ZERO;
	private BigDecimal delivered = BigDecimal.ZERO;
	private BigDecimal accepted = BigDecimal.ZERO;
	private BigDecimal shrinkage = BigDecimal.ZERO;
	private BigDecimal issued = BigDecimal.ZERO;
	private BigDecimal buffer = BigDecimal.ZERO;
	private BigDecimal paidAmount = BigDecimal.ZERO;
	private BigDecimal unitCost = BigDecimal.ZERO;

	private BigDecimal returnedPrice = BigDecimal.ZERO;

	private BigDecimal standardPrice = BigDecimal.ZERO;
	private BigDecimal standardDiscount = BigDecimal.ZERO;

	private BigDecimal dealQuantity = BigDecimal.ZERO;
	private BigDecimal palletQty = BigDecimal.ZERO;
	private BigDecimal palletWeight = BigDecimal.ZERO;
	private BigDecimal roll = BigDecimal.ZERO;
	private BigDecimal onHand = BigDecimal.ZERO;
	private BigDecimal writeOff = BigDecimal.ZERO;

	private Long reference;
	private Long referenceId;

	private ExchangeType exchange;
	private AddressType postalType;
	private SalesType salesType = SalesType.STANDARD;
	private WriteOffType writeOffType;
	private DeliveryOrderItemType deliveryItemType;
	private MaterialType materialType;

	private Facility facility;
	private Grid grid;
	private Container container;
	private Container destination;
	private Container source;

	private Product product;
	private UnitOfMeasure uom;

	private Date date;
	private Currency currency;
	private Tax tax;

	private DeliveryOrderReferenceItem deliveryReferenceItem;
	private BillingReferenceItem billingReferenceItem;
	private WarehouseTransactionItem warehouseTransactionItem;
	private InvoiceVerificationItem invoiceVerificationItem;
	private InvoiceVerification invoiceVerification;
	private CostCenter costCenter;
	private Billing billing;

	private String code;
	private String serial;
	private String note;
	private String lotCode;
	private String palletName;
	private String legend;
	private String referenceCode;
	private String referenceType;
	private String coneMark;

	private boolean enabled;
	private boolean actived;
	private boolean deliverable;
	private boolean approved;

	private MultipartFile file;
	private Geographic geographic;
}
