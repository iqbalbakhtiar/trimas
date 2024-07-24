/**
 * Oct 31, 2008 10:23:41 AM
 * com.siriuserp.sdk.dao
 * PostalAddressDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.AddressType;
import com.siriuserp.sdk.dm.PostalAddress;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface PostalAddressDao extends Dao<PostalAddress>,Filterable
{
    public PostalAddress loadDefault(AddressType type,Long party);
    public PostalAddress loadByDefault(String selected, Long party);
    public List<PostalAddress> loadAll(Long Party);
    public List<PostalAddress> loadByAddressType(Long organizatiton);
}