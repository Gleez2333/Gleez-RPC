package com.gleez.core.registry.api;

import java.net.InetSocketAddress;

/**
 * 服务发现接口
 * @Author Gleez
 * @Date 2020/8/8 13:35
 */
public interface ServiceDiscovery {

    InetSocketAddress lookupService(String serviceName);


}
