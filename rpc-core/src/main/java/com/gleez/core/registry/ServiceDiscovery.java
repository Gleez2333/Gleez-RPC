package com.gleez.core.registry;

import com.gleez.core.loadbanlance.LoadBalance;

import java.net.InetSocketAddress;

/**
 * @Author Gleez
 * @Date 2020/8/8 13:35
 */
public interface ServiceDiscovery {

    InetSocketAddress lookupService(String serviceName);

    void setLoadBalance(LoadBalance loadBalance);


}
