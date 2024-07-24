/**
 * Jan 3, 2008 3:31:52 PM
 * com.siriuserp.sdk.annotation
 * AliasName.java
 */
package com.siriuserp.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.siriuserp.sdk.dm.TableType;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AliasName
{
    public TableType type();
    public String code();
}
