package com.github.chenmingq.common.common.annotation;

import java.lang.annotation.*;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：消息模块
 */

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ReqMapping {

    int id();

}
