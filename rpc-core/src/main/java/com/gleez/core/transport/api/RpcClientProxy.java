package com.gleez.core.transport.api;

import com.gleez.commom.entity.RpcRequest;
import com.gleez.commom.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:06
 * RPC客户端动态代理,通过代理方法把RpcRequest发出去
 */
public class RpcClientProxy implements InvocationHandler {


    private final RpcClient client;

    public RpcClientProxy(RpcClient client) {
        this.client = client;
    }


    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        return ((RpcResponse)client.sendRequest(rpcRequest)).getData();
    }
}
