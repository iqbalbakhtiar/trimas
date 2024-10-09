/**
 * 
 */
package com.siriuserp.sdk.dm;

import java.math.BigDecimal;
import java.util.Date;

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

	private BigDecimal returned = BigDecimal.ZERO;
	private BigDecimal returnedPrice = BigDecimal.ZERO;

	private BigDecimal standardPrice = BigDecimal.ZERO;
	private BigDecimal standardDiscount = BigDecimal.ZERO;

	private BigDecimal dealQuantity = BigDecimal.ZERO;
	private BigDecimal palletQty = BigDecimal.ZERO;
	private BigDecimal palletWeight = BigDecimal.ZERO;
	private BigDecimal roll = BigDecimal.ZERO;
	private BigDecimal onHand = BigDecimal.ZERO;

	private ExchangeType exchange;
	private AddressType postalType;
	private UnitOfMeasure uom;

	private Facility facility;
	private Grid grid;
	private Container container;
	private Container destination;
	private Date date;
	private Currency currency;
	private Tax tax;
	private Product product;

	private Long reference;

	private String code;
	private String serial;
	private String note;
	private String lotCode;
	private String palletName;
	private String legend;

	private boolean enabled;
	private boolean actived;

	private MultipartFile file;
	private Geographic geographic;
}
