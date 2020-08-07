package com.gleez.core.registry;

import java.net.InetSocketAddress;

/**
 * @Author Gleez
 * @Date 2020/8/7 15:01
 */
public interface ServiceRegistry {

    void register(String serviceName, InetSocketAddress inetSocketAddress);

    InetSocketAddress lookupService(String serviceName);

}
