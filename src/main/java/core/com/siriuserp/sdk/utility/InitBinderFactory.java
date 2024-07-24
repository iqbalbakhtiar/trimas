package com.siriuserp.sdk.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.springmvc.editor.EnumEditor;
import com.siriuserp.sdk.springmvc.editor.GenericModelEditor;

/**
 * @author Andres Nodas
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@Component
public class InitBinderFactory
{
	@Autowired
	private GenericModelEditor modelEditor;

	@Autowired
	private EnumEditor enumEditor;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initBinder(WebDataBinder binder, Class... args)
	{
		for (Class clazz : args)
		{
			if (Model.class.isAssignableFrom(clazz))
				binder.registerCustomEditor(clazz, modelEditor.forClass(clazz));
			else if (Enum.class.isAssignableFrom(clazz))
				binder.registerCustomEditor(clazz, enumEditor.forClass(clazz));
		}
	}
}
