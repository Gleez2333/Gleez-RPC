package com.gleez.core.provider;

/**
 * 服务注册接口
 * @Author Gleez
 * @Date 2020/8/4 23:31
 */
public interface ServiceProvider {

    <T> void addServiceProvider(T service);

    Object getServiceProvider(String serviceName);
}
