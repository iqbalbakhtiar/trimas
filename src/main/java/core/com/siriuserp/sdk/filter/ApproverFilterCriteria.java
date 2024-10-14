package com.siriuserp.sdk.filter;

import java.util.Date;

import com.siriuserp.sdk.utility.DateHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApproverFilterCriteria extends AbstractFilterCriteria {
	
	private static final long serialVersionUID = -6082557022224617725L;
	
	private String code;
	private String salutation;
	private String name;
	private String sort;
	private String clean;
	private String approver;
	private String company;
	private String status;
	private Date date;

	private Long facility;
	private Long except;
	
	private Boolean base;
	private Boolean active;
	
	public String getDateString()
	{
		if (this.date != null)
			return DateHelper.format(getDate());

		return null;
	}
}
