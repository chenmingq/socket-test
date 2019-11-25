package com.github.chenmingq.common.common.annotation;

import java.lang.annotation.*;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：注入对象
 */

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AutoIn {

}
