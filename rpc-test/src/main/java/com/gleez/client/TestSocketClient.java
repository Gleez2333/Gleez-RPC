package com.gleez.client;

import com.gleez.api.HelloService;
import com.gleez.commom.enumeration.RegistryType;
import com.gleez.core.annotation.RegistryConfig;
import com.gleez.core.transport.api.RpcClientProxy;
import com.gleez.core.transport.socket.SocketClient;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:57
 */
@RegistryConfig(type = RegistryType.NACOS, address = "127.0.0.1:8848")
public class TestSocketClient {
    public static void main(String[] args) {

        SocketClient client = new SocketClient();
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        String res = helloService.hello("hello, my RPC-Framework");
        System.out.println(res);
    }
}
