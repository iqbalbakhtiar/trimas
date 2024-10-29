/**
 * May 4, 2009 5:24:23 PM
 * com.siriuserp.administration.criteria
 * ContainerFilterCriteria.java
 */
package com.siriuserp.administration.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public class ContainerFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = 3835529002495532441L;

	private String code;
	private String name;
	
	private Long grid;
	private Long product;
	private Long facility;
}
