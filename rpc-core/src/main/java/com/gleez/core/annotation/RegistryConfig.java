package com.gleez.core.annotation;

import com.gleez.commom.enumeration.RegistryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注册中心配置
 * @Author Gleez
 * @Date 2020/8/17 12:45
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegistryConfig {
    public RegistryType type();
    public String address();
}
