/**
 * May 20, 2009 8:47:44 AM
 * com.siriuserp.sdk.annotation
 * AutomaticPosting.java
 */
package com.siriuserp.accounting.posting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface AutomaticPosting
{
    public Class<? extends AbstractPostingRole>[] roleClasses();
    public String process() default "";
	public boolean required() default true;
	public boolean verify() default false;
	public String[] roles() default "";
}
