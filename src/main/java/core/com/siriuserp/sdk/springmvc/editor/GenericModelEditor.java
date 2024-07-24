/**
 * Mar 25, 2008 1:54:00 PM
 * com.siriuserp.sdk.springmvc.editor
 * OrganizationEditor.java
 */
package com.siriuserp.sdk.springmvc.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Khairullah
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class GenericModelEditor extends PropertyEditorSupport
{
	@Autowired
	private GenericDao dao;

	private Class<? extends Model> objectClass;

	public GenericModelEditor()
	{
	}

	private GenericModelEditor(GenericDao dao, Class<? extends Model> objectClass)
	{
		this.dao = dao;
		this.objectClass = objectClass;
	}

	public GenericModelEditor forClass(Class<? extends Model> objectClass)
	{
		this.objectClass = objectClass;
		return new GenericModelEditor(dao, objectClass);
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException
	{
		if (SiriusValidator.validateParamWithZeroPosibility(text))
			setValue(dao.load(objectClass, Long.valueOf(text)));
	}
}
