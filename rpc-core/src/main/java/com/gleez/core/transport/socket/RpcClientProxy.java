package com.gleez.core.transport.socket;

import com.gleez.commom.entity.RpcRequest;
import com.gleez.commom.entity.RpcResponse;
import com.gleez.core.transport.api.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:06
 * RPC客户端动态代理,通过代理方法把RpcRequest发出去
 */
public class RpcClientProxy implements InvocationHandler {

    private String host;
    private int port;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
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
        RpcClient rpcClient = new SocketClient(host, port);
        return ((RpcResponse)rpcClient.sendRequest(rpcRequest)).getData();
    }
}
