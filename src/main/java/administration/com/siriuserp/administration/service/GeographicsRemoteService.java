package com.siriuserp.administration.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.administration.criteria.GeographicFilterCriteria;
import com.siriuserp.administration.dm.Geographic;
import com.siriuserp.administration.dm.GeographicType;
import com.siriuserp.administration.query.GeographicGridViewQuery;
import com.siriuserp.sdk.dao.GenericDao;

import javolution.util.FastMap;

/**
 * @author Muhammad Khairullah
 * @author Iqbal Bakhtiar
 * Sirius Indonesia,PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class GeographicsRemoteService
{
	@Autowired
	private GenericDao genericDao;

	public Map<String, Object> getCountry()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		GeographicGridViewQuery geo = new GeographicGridViewQuery();
		GeographicFilterCriteria filter = new GeographicFilterCriteria();

		filter.setType(GeographicType.COUNTRY);
		geo.setFilterCriteria(filter);
		map.put("items", genericDao.filter(geo));

		return map;
	}

	public Map<String, Object> getProvince(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		GeographicGridViewQuery geo = new GeographicGridViewQuery();
		GeographicFilterCriteria filter = new GeographicFilterCriteria();
		filter.setParentId(id);
		filter.setType(GeographicType.PROVINCE);
		geo.setFilterCriteria(filter);
		map.put("items", genericDao.filter(geo));

		return map;
	}

	public Map<String, Object> getCity(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		GeographicGridViewQuery geo = new GeographicGridViewQuery();
		GeographicFilterCriteria filter = new GeographicFilterCriteria();
		filter.setParentId(id);
		filter.setType(GeographicType.CITY);
		geo.setFilterCriteria(filter);
		map.put("items", genericDao.filter(geo));

		return map;
	}

	public Map<String, Object> checkAvailableRegion(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		return map;
	}

	public Map<String, Object> getRegion(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		GeographicGridViewQuery geo = new GeographicGridViewQuery();
		GeographicFilterCriteria filter = new GeographicFilterCriteria();
		filter.setType(GeographicType.DISTRICT);
		filter.setParentId(id);
		geo.setFilterCriteria(filter);
		map.put("items", genericDao.filter(geo));

		return map;
	}

	public Map<String, Object> getSubdistrict(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		GeographicGridViewQuery geo = new GeographicGridViewQuery();
		GeographicFilterCriteria filter = new GeographicFilterCriteria();
		filter.setType(GeographicType.AREA);
		filter.setParentId(id);
		geo.setFilterCriteria(filter);
		map.put("items", genericDao.filter(geo));

		return map;
	}

	public List<Geographic> getCountryList()
	{
		GeographicGridViewQuery geo = new GeographicGridViewQuery();
		GeographicFilterCriteria filter = new GeographicFilterCriteria();
		filter.setType(GeographicType.COUNTRY);
		geo.setFilterCriteria(filter);

		return genericDao.filter(geo);
	}

	public List<Geographic> getProvinceList()
	{
		GeographicGridViewQuery geo = new GeographicGridViewQuery();
		GeographicFilterCriteria filter = new GeographicFilterCriteria();
		filter.setType(GeographicType.PROVINCE);
		geo.setFilterCriteria(filter);

		return genericDao.filter(geo);
	}

	public List<Geographic> getCityList()
	{
		GeographicGridViewQuery geo = new GeographicGridViewQuery();
		GeographicFilterCriteria filter = new GeographicFilterCriteria();
		filter.setType(GeographicType.CITY);
		geo.setFilterCriteria(filter);

		return genericDao.filter(geo);
	}
}
