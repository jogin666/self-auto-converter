package com.zy.auto.converter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Jong
 * @Date 2021/1/21 9:37
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TargetConvertInfo {

    String targetFieldName() default "";

    boolean isInnerNotStatic() default false;
}
