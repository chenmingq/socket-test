package com.github.chenmingq.common.common.annotation;


import java.lang.annotation.*;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：service实现
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ServiceImpl {
}
