package com.gleez.client;

import com.gleez.api.HelloObject;
import com.gleez.api.HelloService;
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
    //   client.setLoadBalance(new RoundRobinLoadBalance());
        client.setSerializer(new KryoSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(1, "This is a message");
        for(int i=1;i<=10;++i) {
            object.setId(i);
            System.out.println(helloService.hello(object));
        }
      //  HelloService2 helloService2 = rpcClientProxy.getProxy(HelloService2.class);
        //object.setId(13);
        //System.out.println(helloService2.hello2(object));
    }
}
