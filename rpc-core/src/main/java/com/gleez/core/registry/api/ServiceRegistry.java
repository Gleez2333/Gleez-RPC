package com.gleez.core.registry.api;

import java.net.InetSocketAddress;

/**
 * 服务注册接口
 * @Author Gleez
 * @Date 2020/8/7 15:01
 */
public interface ServiceRegistry {

    void register(String serviceName, InetSocketAddress inetSocketAddress);



}
