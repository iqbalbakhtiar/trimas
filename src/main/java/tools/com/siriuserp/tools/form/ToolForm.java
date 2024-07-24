/**
 * 
 */
package com.siriuserp.tools.form;

import java.math.BigDecimal;
import java.util.List;

import com.siriuserp.sdk.dm.FlagLevel;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Role;
import com.siriuserp.sdk.dm.User;

import javolution.util.FastList;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 *
 */

@Setter
@Getter
public class ToolForm extends Form
{
	private static final long serialVersionUID = 7380449330706179706L;

	private String username;
	private String type;

	private FlagLevel flagLevel;

	private boolean enabled;
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;
	private boolean superVisorStatus = false;

	private User user;
	private Role role;

	private BigDecimal autoReset = BigDecimal.ZERO;

	private Integer timer;

	private final List<Item> grids = new FastList<Item>();
	private final List<Item> gridRoles = new FastList<Item>();
	private final List<Item> categories = new FastList<Item>();
	private final List<Item> prices = new FastList<Item>();
	private final List<Item> banks = new FastList<Item>();
	private final List<Item> approvals = new FastList<Item>();
}
