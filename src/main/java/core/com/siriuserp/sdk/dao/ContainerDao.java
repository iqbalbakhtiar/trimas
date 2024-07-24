/**
 * Sep 20, 2006 1:31:11 PM
 * net.konsep.sirius.administration.dao
 * ContainerDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Container;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface ContainerDao extends Dao<Container>, Filterable
{
	public Container loadByCode(String code);
	public Container loadByBarcode(String barcode);
	public Container loadDefaultContainer(Long facility);
	public Container loadDefaultContainer(Long facility, Long container);
	public List<Container> loadContainerByFacility(Long facility);
}
