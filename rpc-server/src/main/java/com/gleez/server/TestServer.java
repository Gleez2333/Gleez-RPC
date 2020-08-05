package com.gleez.server;

import com.gleez.api.HelloService;
import com.gleez.api.HelloService2;
import com.gleez.core.registry.DefaultServiceRegistry;
import com.gleez.core.registry.ServiceRegistry;
import com.gleez.core.transport.RpcServer;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:55
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        HelloService2 helloService2 = new HelloService2Impl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.registry(helloService);
        serviceRegistry.registry(helloService2);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
