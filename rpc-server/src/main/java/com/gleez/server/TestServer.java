package com.gleez.server;

import com.gleez.api.HelloService;
import com.gleez.core.RpcServer;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:55
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}
