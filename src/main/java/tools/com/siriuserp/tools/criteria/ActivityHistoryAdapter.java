/**
 * Mar 29, 2007 11:59:06 AM
 * net.konsep.sirius.presentation.DTO
 * ActivityHistoryDTO.java
 */
package com.siriuserp.tools.criteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.siriuserp.sdk.dm.ActivityHistory;
import com.siriuserp.sdk.dm.Party;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 */

@Getter
@Setter
public class ActivityHistoryAdapter implements Serializable
{
	private static final long serialVersionUID = 370120453386043066L;

	private String mode;

	private ActivityHistory activityHistory;
	private List<ActivityHistory> activityHistorys = new ArrayList<ActivityHistory>();
	private AuditTrailsFilterCriteria filterCriteria;
	private List<Party> persons = new ArrayList<Party>();
}
