package com.gleez.client;

import com.gleez.api.HelloService;
import com.gleez.core.serializer.KryoSerializer;
import com.gleez.core.transport.api.RpcClientProxy;
import com.gleez.core.transport.socket.SocketClient;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:57
 */
public class TestSocketClient {
    public static void main(String[] args) {

        SocketClient client = new SocketClient();
        client.setSerializer(new KryoSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        String res = helloService.hello("hello, my RPC-Framework");
        System.out.println(res);
    }
}
