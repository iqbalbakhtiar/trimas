package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author ferdinand
 */

@Getter
@Setter
@NoArgsConstructor
public class TransferOrderFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = -962348837075449871L;

    private String code;

    private Long source;
    private Long destination;

    private Date dateFrom;
    private Date dateTo;

    private String sourceName;
    private String destinationName;
}
