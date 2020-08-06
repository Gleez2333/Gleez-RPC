package com.gleez.server;

import com.gleez.api.HelloService;
import com.gleez.api.HelloService2;
import com.gleez.core.registry.DefaultServiceRegistry;
import com.gleez.core.registry.ServiceRegistry;
import com.gleez.core.transport.api.RpcServer;
import com.gleez.core.transport.netty.server.NettyServer;

/**
 * @Author Gleez
 * @Date 2020/8/5 16:22
 */
public class TestNettyServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        HelloService2 helloService2 = new HelloService2Impl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.registry(helloService);
        registry.registry(helloService2);
        RpcServer server = new NettyServer();
        server.start(9000);
    }
}
