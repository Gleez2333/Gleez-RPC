package com.gleez.client;

import com.gleez.api.HelloObject;
import com.gleez.api.HelloService;
import com.gleez.api.HelloService2;
import com.gleez.core.transport.api.RpcClient;
import com.gleez.core.transport.socket.RpcClientProxy;
import com.gleez.core.transport.socket.SocketClient;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:57
 */
public class TestSocketClient {
    public static void main(String[] args) {
        RpcClient rpcClient = new SocketClient("127.0.0.1", 9000);
        RpcClientProxy proxy = new RpcClientProxy(rpcClient);
        HelloService helloService1 = proxy.getProxy(HelloService.class);
        HelloService2 helloService2 = proxy.getProxy(HelloService2.class);
        HelloObject object = new HelloObject(12, "This is a message");
        for (int i = 0; i < 100; i++) {
            object.setId(i);
            object.setMessage("Now is " + i);
            if(i % 3 == 0)
                System.out.println(helloService1.hello(object));
            else
                System.out.println(helloService2.hello2(object));
        }
    }
}