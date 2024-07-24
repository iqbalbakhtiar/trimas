package com.siriuserp.sdk.dm;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Currency extends Model implements JSONSupport
{
	private static final long serialVersionUID = -6500427629800391288L;

	@Column(name = "name", nullable = false, unique = true, length = 100)
	private String name;

	@Column(name = "symbol", nullable = false, unique = true, length = 50)
	private String symbol;

	@Column(name = "description", length = 255)
	private String description;

	@Column(name = "is_default", length = 1)
	@Type(type = "yes_no")
	private boolean base;

	public static final synchronized Currency newInstance(String id)
	{
		if (SiriusValidator.validateParam(id))
		{
			Currency currency = new Currency();
			currency.setId(Long.valueOf(id));

			return currency;
		}

		return null;
	}

	@Override
	public Map<String, Object> val()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("currId", getId());
		map.put("currSymbol", getSymbol());

		return map;
	}

	@Override
	public String getAuditCode()
	{
		return this.symbol + "," + this.name;
	}
}
