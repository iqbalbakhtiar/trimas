/**
 * Nov 1, 2007 11:16:12 AM
 * com.siriuserp.sdk.annotation
 * AuditTrails.java
 */
package com.siriuserp.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.siriuserp.sdk.dm.Model;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AuditTrails
{
    public Class<? extends Model> className();
    public AuditTrailsActionType actionType();
}
