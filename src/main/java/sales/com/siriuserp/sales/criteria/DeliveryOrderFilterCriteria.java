package com.siriuserp.sales.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryOrderFilterCriteria extends AbstractFilterCriteria {

    private static final long serialVersionUID = -6328872532983743382L;

    private String code;
    private String customer;
    private String driver;
    private String vehicle;
    private String facility;
    private String shippingAddress;
    private String status;

    private Date date;
}
