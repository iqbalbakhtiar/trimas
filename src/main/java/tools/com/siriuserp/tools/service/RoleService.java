/**
 * Nov 27, 2008 4:04:11 PM
 * com.siriuserp.administration.service
 * RoleService.java
 */
package com.siriuserp.tools.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.AccessibleModuleDao;
import com.siriuserp.sdk.dao.CompanyStructureDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.ModuleDao;
import com.siriuserp.sdk.dao.OrganizationRoleDao;
import com.siriuserp.sdk.dao.RoleDao;
import com.siriuserp.sdk.dm.AccessType;
import com.siriuserp.sdk.dm.AccessibleModule;
import com.siriuserp.sdk.dm.Module;
import com.siriuserp.sdk.dm.OrganizationRole;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Role;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.tools.adapter.RoleDetailUIAdapter;
import com.siriuserp.tools.adapter.RoleUIAdapter;
import com.siriuserp.tools.query.RoleGridViewQuery;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class RoleService
{
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private ModuleDao moduleDao;

	@Autowired
	private AccessibleModuleDao accessibleModuleDao;

	@Autowired
	private OrganizationRoleDao organizationRoleDao;

	@Autowired
	private CompanyStructureDao companyStructureDao;

	@Autowired
	private GenericDao genericDao;

	@AuditTrails(className = Role.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Role role) throws ServiceException
	{
		for (AccessibleModule accessibleModule : accessibleModuleDao.loadAll(role.getId()))
			genericDao.delete(accessibleModule);

		for (OrganizationRole organizationRole : organizationRoleDao.loadAll(role.getId()))
			genericDao.delete(organizationRole);

		roleDao.delete(role);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		RoleUIAdapter adapter = new RoleUIAdapter();
		adapter.setRole(new Role());
		adapter.getAccessModules().addAll(moduleDao.loadIsNotMapped(new FastList<Long>()));
		adapter.getOrganizationRoles().addAll(companyStructureDao.loadIsNotMapped(new FastList<Long>()));
		adapter.getTypes().addAll(genericDao.loadAll(AccessType.class));

		Collections.sort(adapter.getAccessModules());

		map.put("adapter", adapter);

		return map;
	}

	@AuditTrails(className = Role.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Role role, RoleUIAdapter adapter) throws ServiceException
	{
		for (RoleDetailUIAdapter access : adapter.getAccessModules())
		{
			AccessibleModule module = new AccessibleModule();
			module.setEnabled(access.isEnabled());
			module.setAccessType(access.getType());
			module.setRole(adapter.getRole());

			if (module.getModule() == null)
				module.setModule(genericDao.load(Module.class, access.getModule()));

			genericDao.add(module);
		}

		for (RoleDetailUIAdapter access : adapter.getOrganizationRoles())
		{
			OrganizationRole module = new OrganizationRole();
			module.setEnabled(access.isEnabled());
			module.setRole(adapter.getRole());

			if (module.getOrganization() == null)
				module.setOrganization(genericDao.load(Party.class, access.getModule()));

			genericDao.add(module);
		}

		roleDao.add(role);

		for (Module module : moduleDao.loadALLMandatory())
		{
			AccessibleModule accessibleModule = new AccessibleModule();
			accessibleModule.setAccessType(genericDao.load(AccessType.class, Long.valueOf(6)));
			accessibleModule.setEnabled(true);
			accessibleModule.setModule(module);
			accessibleModule.setRole(role);

			genericDao.add(accessibleModule);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> precopy(RoleUIAdapter adapter)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		RoleUIAdapter target = new RoleUIAdapter();
		target.setRole(new Role());
		target.getTypes().addAll(adapter.getTypes());
		target.getAccessModules().addAll(adapter.getAccessModules());
		target.getOrganizationRoles().addAll(adapter.getOrganizationRoles());

		map.put("adapter", target);

		return map;
	}

	@AuditTrails(className = Role.class, actionType = AuditTrailsActionType.CREATE)
	public void copy(Role role, RoleUIAdapter adapter) throws ServiceException
	{
		for (RoleDetailUIAdapter access : adapter.getAccessModules())
		{
			AccessibleModule module = new AccessibleModule();
			module.setEnabled(access.isEnabled());
			module.setAccessType(access.getType());
			module.setRole(role);

			if (module.getModule() == null)
				module.setModule(genericDao.load(Module.class, access.getModule()));

			accessibleModuleDao.add(module);
		}

		for (RoleDetailUIAdapter access : adapter.getOrganizationRoles())
		{
			OrganizationRole module = new OrganizationRole();
			module.setEnabled(access.isEnabled());
			module.setRole(role);

			if (module.getOrganization() == null)
				module.setOrganization(genericDao.load(Party.class, access.getModule()));

			organizationRoleDao.add(module);
		}

		roleDao.add(role);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		FastList<Long> modules = new FastList<Long>();
		FastList<Long> orgs = new FastList<Long>();

		RoleUIAdapter adapter = new RoleUIAdapter(load(id));
		adapter.getTypes().addAll(genericDao.loadAll(AccessType.class));
		adapter.getAccessModules().addAll(moduleDao.loadIsMapped(id));
		adapter.getOrganizationRoles().addAll(companyStructureDao.loadIsMapped(id));

		for (RoleDetailUIAdapter access : adapter.getAccessModules())
			modules.add(access.getModule());

		for (RoleDetailUIAdapter access : adapter.getOrganizationRoles())
			orgs.add(access.getModule());

		adapter.getAccessModules().addAll(moduleDao.loadIsNotMapped(modules));
		adapter.getOrganizationRoles().addAll(companyStructureDao.loadIsNotMapped(orgs));

		Collections.sort(adapter.getAccessModules());

		map.put("adapter", adapter);

		return map;
	}

	@AuditTrails(className = Role.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(RoleUIAdapter adapter) throws ServiceException
	{
		for (RoleDetailUIAdapter access : adapter.getAccessModules())
		{
			AccessibleModule module = null;
			boolean status = false;

			if (SiriusValidator.validateLongParam(access.getId()))
				module = genericDao.load(AccessibleModule.class, access.getId());

			if (module == null)
			{
				module = new AccessibleModule();
				module.setRole(adapter.getRole());

				status = true;
			}

			module.setEnabled(access.isEnabled());
			module.setAccessType(access.getType());

			if (module.getModule() == null)
				module.setModule(genericDao.load(Module.class, access.getModule()));

			if (status)
				accessibleModuleDao.add(module);
			else
				accessibleModuleDao.update(module);
		}

		roleDao.update(adapter.getRole());
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("roles", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, RoleGridViewQuery.class)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Role load(Long id)
	{
		return genericDao.load(Role.class, id);
	}
}
