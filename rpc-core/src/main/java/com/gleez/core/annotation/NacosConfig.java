package com.gleez.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Gleez
 * @Date 2020/8/10 15:52
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NacosConfig {
    public String value() default "127.0.0.1:8848";
}
