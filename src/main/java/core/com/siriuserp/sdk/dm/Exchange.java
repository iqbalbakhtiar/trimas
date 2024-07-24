/**
 * Dec 7, 2007 10:11:03 AM
 * net.konsep.sirius.model
 * Exchange.java
 */
package com.siriuserp.sdk.dm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.utility.DateHelper;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Entity
@Table(name="exchange")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Exchange extends Model implements JSONSupport
{
    private static final long serialVersionUID = -8776816144201523155L;
    
    @Column(name="rate",nullable=false)
    private BigDecimal rate;
        
    @Column(name="valid_from",nullable=false)
    private Date validFrom;
    
    @Column(name="type",nullable=false)
    @Enumerated(EnumType.STRING)
    private ExchangeType type;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_currency_from")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Currency from;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_currency_to")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Currency to;
    
    public Exchange(){}
    
    public ExchangeType getType()
    {
        return type;
    }

    public void setType(ExchangeType type)
    {
        this.type = type;
    }

    public Currency getFrom()
    {
        return from;
    }

    public void setFrom(Currency currency)
    {
        this.from = currency;
    }    

    public BigDecimal getRate()
    {
        return rate;
    }

    public void setRate(BigDecimal rate)
    {
        this.rate = rate;
    }

    public Currency getTo()
    {
        return to;
    }

    public void setTo(Currency to)
    {
        this.to = to;
    }

    public Date getValidFrom()
    {
        return validFrom;
    }

    public void setValidFrom(Date validFrom)
    {
        this.validFrom = validFrom;
    }
    
    public static final synchronized Exchange newInstance(BigDecimal rate)
    {
        Exchange exchange = new Exchange();
        exchange.setRate(rate);
        
        return exchange;
    }

    @Override
    public String getAuditCode()
    {
        return this.id+","+this.rate;
    }

	@Override
	public Map<String, Object> val() {
		
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("id", getId());
		map.put("rate", getRate());
		map.put("validFrom", DateHelper.FORMATTER.format(getValidFrom()));
		map.put("type", getType());
		map.put("from", getFrom());
		map.put("to", getTo());
		
		return map;
	}
}
