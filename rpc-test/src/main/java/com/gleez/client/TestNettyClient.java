package com.gleez.client;

import com.gleez.api.ByeService;
import com.gleez.api.HelloService;
import com.gleez.commom.enumeration.RegistryType;
import com.gleez.core.annotation.RegistryConfig;
import com.gleez.core.loadbanlance.RandomLoadBalance;
import com.gleez.core.serializer.ProtobufSerializer;
import com.gleez.core.transport.api.RpcClient;
import com.gleez.core.transport.api.RpcClientProxy;
import com.gleez.core.transport.netty.client.NettyClient;

/**
 * @Author Gleez
 * @Date 2020/8/5 16:10
 */
@RegistryConfig(type = RegistryType.ZOOKEEPER, address = "127.0.0.1:2181")
public class TestNettyClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient(new ProtobufSerializer(), new RandomLoadBalance());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        for(int i=1;i<=10;++i) {
            System.out.println(helloService.hello("第 " + i + " : 个消息"));
        }
        for (int i = 1; i <= 10; i++) {
            System.out.println(byeService.bye(" " + i));
        }

    }
}
