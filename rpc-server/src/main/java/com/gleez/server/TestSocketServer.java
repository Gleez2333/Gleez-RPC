package com.gleez.server;

import com.gleez.api.HelloService;
import com.gleez.api.HelloService2;
import com.gleez.core.serializer.KryoSerializer;
import com.gleez.core.transport.socket.SocketServer;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:55
 */
public class TestSocketServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        HelloService2 helloService2 = new HelloService2Impl();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9998);
        socketServer.setSerializer(new KryoSerializer());
        socketServer.publishService(helloService, HelloService.class);
        socketServer.publishService(helloService2, HelloService2.class);
        socketServer.start();
    }
}
