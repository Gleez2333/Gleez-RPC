package com.gleez.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * nacos配置注解
 * @Author Gleez
 * @Date 2020/8/10 15:52
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NacosConfig {
    public String value() default "";
}
