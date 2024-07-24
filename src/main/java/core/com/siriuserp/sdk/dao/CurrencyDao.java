package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.Currency;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface CurrencyDao extends Dao<Currency>
{
    public Currency loadDefaultCurrency();
    public List<Currency> loadNonDefault();
    public Currency loadIfNot(Long id);
}
