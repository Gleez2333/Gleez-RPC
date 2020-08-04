package com.gleez.core.registry;

/**
 * 服务注册接口
 * @Author Gleez
 * @Date 2020/8/4 23:31
 */
public interface ServiceRegistry {
    <T> void registry(T service);
    Object getService(String serviceName);
}
