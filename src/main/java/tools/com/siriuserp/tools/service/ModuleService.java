/**
 * Nov 16, 2008 5:49:18 PM
 * com.siriuserp.tools.service
 * ModuleService.java
 */
package com.siriuserp.tools.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.AccessibleModuleDao;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.ModuleDao;
import com.siriuserp.sdk.dm.AccessType;
import com.siriuserp.sdk.dm.AccessibleModule;
import com.siriuserp.sdk.dm.CodeSequence;
import com.siriuserp.sdk.dm.Module;
import com.siriuserp.sdk.dm.ModuleDetail;
import com.siriuserp.sdk.dm.ModuleGroup;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.dm.Role;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.tools.criteria.ModuleFilterCriteria;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class ModuleService
{
    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private AccessibleModuleDao accessibleModuleDao;
    
    @Autowired
    private CodeSequenceDao codeSequenceDao;
    
    @Autowired
    private GenericDao genericDao;
    
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preadd(Long id)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        
        Module module = new Module();
        module.setModuleGroup(genericDao.load(ModuleGroup.class, id));
        
        map.put("module",module);
        
        return map;
    }
    
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> precopy(Long id, Long module)
	{
		Module source = load(module);
		
		FastMap<String, Object> map = new FastMap<String, Object>();

		Module target = new Module();
		BeanUtils.copyProperties(source, target, "details");
		target.setOldKey(source.getDefaultUri().replace("/page/", "").replace("view.htm", ""));
		
		for(ModuleDetail moduleDetail : source.getDetails()) {
			ModuleDetail detail = new ModuleDetail();
			detail.setModule(target);
			detail.setUri(moduleDetail.getUri());
			detail.setDetailType(moduleDetail.getDetailType());
			
			target.getDetails().add(detail);
		}
		
		map.put("module", target);

		return map;
	}
    
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preedit(Long id)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("module",load(id));
        
        return map;
    }
    
    @AuditTrails(className=Module.class,actionType=AuditTrailsActionType.CREATE)
    public void add(Module module) throws Exception {
    	module.setCode(GeneratorHelper.instance().generate(TableType.MODULE, codeSequenceDao, module.getModuleGroup().getCode(), DateHelper.toStartDate(Month.JANUARY, 2001), CodeSequence.YEAR));
    	
    	if (module.getAlias() == null || module.getAlias().trim().length() == 0)
			module.setAlias(module.getName());
		
		module.setNewKey(module.getDefaultUri().replace("/page/", "").replace("view.htm", ""));
		
		for(ModuleDetail moduleDetail : module.getDetails()) {
			moduleDetail.setModule(module);
			moduleDetail.setUri(moduleDetail.getUri().replace(module.getOldKey(), module.getNewKey()));
		}

        moduleDao.add(module);
        
        Role role = genericDao.load(Role.class, Long.valueOf(2));
        if(role != null)
        {
            AccessibleModule accessibleModule = new AccessibleModule();
            accessibleModule.setRole(role);
            accessibleModule.setAccessType(genericDao.load(AccessType.class, Long.valueOf(6)));
            accessibleModule.setEnabled(true);
            accessibleModule.setModule(module);
            
            accessibleModuleDao.add(accessibleModule);
        }
    }
    
    @AuditTrails(className=Module.class,actionType=AuditTrailsActionType.UPDATE)
    public void edit(Module module) throws Exception {
        moduleDao.update(module);
    }
    
    @AuditTrails(className=Module.class,actionType=AuditTrailsActionType.DELETE)
    public void delete(Module module) throws Exception {
        moduleDao.delete(module);
    }
    
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public Module load(Long id)
    {
        return genericDao.load(Module.class, id);
    }
    
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public boolean check(ModuleFilterCriteria criteria) throws Exception, UnsupportedEncodingException
	{
    	if(SiriusValidator.validateParam(criteria.getCode()))
    	{
    		Module module = genericDao.getUniqeField(Module.class, "code", criteria.getCode());
    		if(module != null)
    			return true;
    	}

    	if(SiriusValidator.validateParam(criteria.getName()))
    	{
    		Module module = genericDao.getUniqeField(Module.class, "name", URLDecoder.decode(criteria.getName(), "UTF-8"));
    		if(module != null)
    			return true;
    	}
    	
    	if(SiriusValidator.validateParam(criteria.getUri()))
    	{
    		Module module = genericDao.getUniqeField(Module.class, "defaultUri", URLDecoder.decode(criteria.getUri(), "UTF-8"));
    		if(module != null)
    			return true;
    	}

    	return false;
	}
}
