/**
 * Jun 8, 2009 11:16:06 AM
 * com.siriuserp.sdk.annotation
 * InjectParty.java
 */
package com.siriuserp.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface InjectParty
{
    public String keyName() default "";
    public Class<?> returnClass() default Map.class;
    public String targetMethod() default "setOrganization";
    public String sourceMethod() default "getOrganization";
}
