/**
 * 
 */
package com.siriuserp.accounting.adapter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonValue;

import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.utility.DateHelper;

import lombok.Getter;
import lombok.Setter;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Getter
@Setter
public class JournalEntryDetailAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = 6610813675079196385L;

	private Long id;
	private String code;
	private String note;

	private Date date;

	private GLPostingType type;
	private BigDecimal amount;

	public JournalEntryDetailAdapter(Long id, String code, String note, Date date, GLPostingType type, BigDecimal amount)
	{
		this.id = id;
		this.code = code;
		this.note = note;
		this.date = date;
		this.type = type;
		this.amount = amount;
	}

	@JsonValue
	public Map<String, Object> val()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", getId());
		map.put("date", DateHelper.format(getDate()));
		map.put("code", getCode());
		map.put("note", getNote());
		map.put("debet", getType().compareTo(GLPostingType.DEBET) == 0 ? getAmount() : BigDecimal.ZERO);
		map.put("credit", getType().compareTo(GLPostingType.CREDIT) == 0 ? getAmount() : BigDecimal.ZERO);
		map.put("balance", getAmount().compareTo(BigDecimal.ZERO) < 0 && getType().compareTo(GLPostingType.DEBET) == 0 ? getAmount().negate() : getAmount());

		return map;
	}
}
