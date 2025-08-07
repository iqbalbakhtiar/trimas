package com.siriuserp.administration.service;

import com.siriuserp.sdk.dm.CustomerStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountpayable.dm.PaymentCollectingType;
import com.siriuserp.accountpayable.dm.PaymentMethod;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.CoreTax;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.TrxCode;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class CustomerService extends Service {
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws ServiceException {
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("customers", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "customer_add")
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("customer_add", new Party());
		map.put("statuses", CustomerStatus.values());

		return map;
	}
	
	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.CREATE)
	public FastMap<String, Object> add(Party customer) throws Exception {
		customer.setCode(GeneratorHelper.instance().generate(TableType.CUSTOMER, codeSequenceDao));
		customer.setBase(false); // false, Karena bukan Group
		
		genericDao.add(customer);
		
		//Add Party Relationship
		PartyRelationship relationship = new PartyRelationship();
		//Party
		relationship.setPartyFrom(customer);
		relationship.setPartyTo(customer.getOrganization()); // Company (Party) 
		//Relation Type
		relationship.setRelationshipType(genericDao.load(PartyRelationshipType.class, PartyRelationshipType.CUSTOMER_RELATIONSHIP));
		//Party Role
		relationship.setPartyRoleTypeFrom(genericDao.load(PartyRoleType.class, PartyRoleType.CUSTOMER));
		relationship.setPartyRoleTypeTo(genericDao.load(PartyRoleType.class, PartyRoleType.SUPPLIER));
		relationship.setCreatedBy(getPerson());
		relationship.setCreatedDate(DateHelper.now());
		genericDao.add(relationship);
		
		//Add Default Payment Method
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setParty(customer);
		paymentMethod.setPaymentType(PaymentMethodType.CASH);
		paymentMethod.setPaymentCollectingType(PaymentCollectingType.DIRECT_TRANSFER);
		paymentMethod.setCreatedBy(getPerson());
		paymentMethod.setCreatedDate(DateHelper.now());
		
		genericDao.add(paymentMethod);
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("customer_group", customer);
		map.put("id", relationship.getId());
		
		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception {
		PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, id);
		
		Party customer = genericDao.load(Party.class, partyRelationship.getPartyFrom().getId());
	    // Initialize paymentMethod
	    customer.getPaymentMethod();
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("customer_edit", customer);
		map.put("relationship", partyRelationship);
	    map.put("trxCodes", TrxCode.values());
		map.put("statuses", CustomerStatus.values());
		
		return map;
	}
	
	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.UPDATE)
	public FastMap<String, Object> edit(Party customer, Long relationshipId) throws Exception {
		genericDao.update(customer);
		
		//Update Payment Method
		PaymentMethod paymentMethod = customer.getPaymentMethod();
		paymentMethod.setUpdatedBy(getPerson());
		paymentMethod.setUpdatedDate(DateHelper.now());
		genericDao.update(paymentMethod);
		
		//Update Party Relationship
		PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, relationshipId);
		partyRelationship.setActive(customer.isActive());
		partyRelationship.setUpdatedBy(getPerson());
		partyRelationship.setCreatedDate(DateHelper.now());
		genericDao.update(partyRelationship);
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("relationshipId", relationshipId);
		
		//Core Tax Info add
		if(customer.getCoreTax() != null) {
			
			if(customer.getCoreTax().getId() == null) {
				
				CoreTax coreTax = new CoreTax();
			 
				BeanUtils.copyProperties(customer.getCoreTax(),coreTax);
				coreTax.setParty(customer);
				genericDao.add(coreTax);
				
			} else {
				
				CoreTax coreTax = genericDao.load(CoreTax.class, customer.getCoreTax().getId());
				BeanUtils.copyProperties(customer.getCoreTax(),coreTax);
				genericDao.update(coreTax);
				
			}
			 
		}
			
		
		return map;
	}
	
	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Long id) throws Exception {
		PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, id);
		partyRelationship.setActive(false);
		
		genericDao.update(partyRelationship);

		Party customer = genericDao.load(Party.class, partyRelationship.getPartyFrom().getId());
		customer.setActive(false);

		genericDao.update(customer);
	}
}
