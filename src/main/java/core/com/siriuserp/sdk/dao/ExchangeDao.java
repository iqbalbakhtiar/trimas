/**
 * Oct 16, 2008 5:00:11 PM
 * com.siriuserp.sdk.dao
 * ExchangeDao.java
 */
package com.siriuserp.sdk.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Exchange;
import com.siriuserp.sdk.dm.ExchangeType;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface ExchangeDao extends Dao<Exchange>,Filterable
{
    public Exchange load(Currency from,Currency to,Date date,ExchangeType type);
    public Exchange load(Long from,Long to,Date date,ExchangeType type, String operator);
    public Exchange load(Long from,Long to,Date date,ExchangeType type, String operator, BigDecimal rate);
    public List<Exchange> loadToday();
}
