package com.siriuserp.sales.form;

import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.sales.dm.DeliveryOrder;
import com.siriuserp.sales.dm.DeliveryOrderRealization;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.utility.DecimalHelper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryOrderForm extends Form {

    private static final long serialVersionUID = 2620305483360699741L;

    private Long id;

    private String vehicle;
    private String driver;
    private String noteExt;
    private Date acceptanceDate;
    private Date returnDate;

    private BigDecimal rit;

    private boolean realization;

    private SOStatus status;
    private Party expedition;

    private DeliveryOrder deliveryOrder;
    private SalesOrder salesOrder;
    private DeliveryOrderRealization deliveryOrderRealization;

    private PostalAddress shippingAddress;
    
    public BigDecimal getTotalLineItemAmountForPrint() {
        return getItems().stream().map(item -> item.getSalesReferenceItem().getTotalAmountPerItemDiscounted()).collect(DecimalHelper.sum());
    }
}
