package com.gleez.client;

import com.gleez.api.HelloObject;
import com.gleez.api.HelloService;
import com.gleez.api.HelloService2;
import com.gleez.core.transport.api.RpcClient;
import com.gleez.core.transport.netty.client.NettyClient;
import com.gleez.core.transport.socket.RpcClientProxy;

/**
 * @Author Gleez
 * @Date 2020/8/5 16:10
 */
public class TestNettyClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9000);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(13, "This is a message!");
        String res = helloService.hello(helloObject);
        System.out.println(res);
        HelloService2 helloService2 = rpcClientProxy.getProxy(HelloService2.class);
        helloObject.setMessage("这是message2");
        helloObject.setId(16);
        String s = helloService2.hello2(helloObject);
        System.out.println(s);

    }
}
