package com.gleez.client;

import com.gleez.api.HelloObject;
import com.gleez.api.HelloService;
import com.gleez.api.HelloService2;
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
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
        HelloService2 helloService2 = proxy.getProxy(HelloService2.class);
        System.out.println(helloService2.hello2(object));
    }
}
