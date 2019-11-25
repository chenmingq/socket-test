package com.github.chenmingq.common.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：控制器包扫描
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScanMapping {
    String name() default "";
}