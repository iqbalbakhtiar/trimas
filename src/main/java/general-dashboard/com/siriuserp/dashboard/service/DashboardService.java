package com.siriuserp.dashboard.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.RestApiConfiguration;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.SessionHelper;
import com.siriuserp.sdk.utility.TokenGenerator;

@Component
public class DashboardService {

	@Autowired
	private GenericDao genericDao;
	
	public void generateToken(User user, HttpSession session, int expiry, Resource pub, Resource key) 
		throws ServiceException, CertificateException, JOSEException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		
    	String token = (String) session.getAttribute(SessionHelper.TOKEN_API);
    	RestApiConfiguration restApiConfiguration = genericDao.load(RestApiConfiguration.class, 1L);
    	
    	if(restApiConfiguration.isActive() && token == null && restApiConfiguration != null)
            session.setAttribute(SessionHelper.TOKEN_API, TokenGenerator.build(user, expiry, pub, key));
	}
}
