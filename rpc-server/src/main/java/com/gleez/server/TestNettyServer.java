package com.gleez.server;

import com.gleez.api.HelloService;
import com.gleez.api.HelloService2;
import com.gleez.core.serializer.KryoSerializer;
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
        RpcServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new KryoSerializer());
        server.publishService(helloService, HelloService.class);
        server.publishService(helloService2, HelloService2.class);
        server.start();
    }
}
