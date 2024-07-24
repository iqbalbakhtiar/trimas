/**
 * Sep 4, 2008 9:16:57 AM
 * com.siriuserp.sdk.controller
 * GenericController.java
 */
package com.siriuserp.sdk.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.siriuserp.sdk.springmvc.editor.GenericEnumEditor;
import com.siriuserp.sdk.springmvc.editor.GenericModelEditor;
import com.siriuserp.sdk.utility.FilterCriteriaFactory;
import com.siriuserp.sdk.utility.InitBinderFactory;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public abstract class ControllerBase
{
	@Autowired
	protected FilterCriteriaFactory criteriaFactory;

	@Autowired
	protected InitBinderFactory initBinderFactory;
	
	@Autowired
	protected GenericModelEditor modelEditor;

	@Autowired
	protected GenericEnumEditor enumEditor;
}