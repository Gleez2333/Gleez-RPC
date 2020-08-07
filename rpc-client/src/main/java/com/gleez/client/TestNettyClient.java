package com.gleez.client;

import com.gleez.api.HelloObject;
import com.gleez.api.HelloService;
import com.gleez.api.HelloService2;
import com.gleez.core.serializer.KryoSerializer;
import com.gleez.core.transport.api.RpcClient;
import com.gleez.core.transport.api.RpcClientProxy;
import com.gleez.core.transport.netty.client.NettyClient;

/**
 * @Author Gleez
 * @Date 2020/8/5 16:10
 */
public class TestNettyClient {
    public static void main(String[] args) {

        RpcClient client = new NettyClient();
        client.setSerializer(new KryoSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
        HelloService2 helloService2 = rpcClientProxy.getProxy(HelloService2.class);
        object.setId(13);
        System.out.println(helloService2.hello2(object));
    }
}
