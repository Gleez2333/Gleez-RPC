package com.gleez.client;

import com.gleez.api.HelloObject;
import com.gleez.api.HelloService;
import com.gleez.core.RpcClientProxy;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:57
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        for (int i = 0; i < 10000; i++) {
            object.setId(i);
            object.setMessage("Now is " + i);
            System.out.println(helloService.hello(object));
        }
    }
}
