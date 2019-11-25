package com.github.chenmingq.common.common.annotation;


import java.lang.annotation.*;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：mapper对象扫描
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MapperScan {

    /**
     * @return
     */
    String name() default "";
}
