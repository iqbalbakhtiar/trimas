package com.siriuserp.sales.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryOrderRealizationFilterCriteria extends AbstractFilterCriteria {

    private static final long serialVersionUID = -8948555592203140610L;

    private String code;
    private String facility;
    private String org;
    private String driver;
    private String customer;

}
