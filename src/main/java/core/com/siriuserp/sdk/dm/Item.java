/**
 * 
 */
package com.siriuserp.sdk.dm;

import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.accounting.dm.BillingReferenceItem;
import com.siriuserp.sales.dm.SalesReferenceItem;
import com.siriuserp.sales.dm.SalesType;
import org.springframework.web.multipart.MultipartFile;

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
	private BigDecimal assigned = BigDecimal.ZERO;
	private BigDecimal price = BigDecimal.ZERO;
	private BigDecimal rate = BigDecimal.ZERO;
	private BigDecimal discount = BigDecimal.ZERO;
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

	private BigDecimal returnedPrice = BigDecimal.ZERO;

	private BigDecimal standardPrice = BigDecimal.ZERO;
	private BigDecimal standardDiscount = BigDecimal.ZERO;

	private BigDecimal dealQuantity = BigDecimal.ZERO;
	private BigDecimal palletQty = BigDecimal.ZERO;
	private BigDecimal palletWeight = BigDecimal.ZERO;
	private BigDecimal roll = BigDecimal.ZERO;
	private BigDecimal onHand = BigDecimal.ZERO;

	private Long referenceId;

	private ExchangeType exchange;
	private AddressType postalType;
	private SalesType salesType = SalesType.STANDARD;
	private UnitOfMeasure uom;

	private Facility facility;
	private Grid grid;
	private Container container;
	private Container destination;
	private Date date;
	private Currency currency;
	private Tax tax;
	private Product product;
	private SalesReferenceItem salesReferenceItem;
	private BillingReferenceItem billingReferenceItem;

	private Long reference;

	private String code;
	private String serial;
	private String note;
	private String lotCode;
	private String palletName;
	private String legend;
	private String referenceCode;

	private boolean enabled;
	private boolean actived;
	private boolean deliverable;
	private boolean approved;

	private MultipartFile file;
	private Geographic geographic;
}
