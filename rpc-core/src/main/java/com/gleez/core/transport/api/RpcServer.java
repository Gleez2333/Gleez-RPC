package com.gleez.core.transport.api;

/**
 * RPC服务端接口
 * @Author Gleez
 * @Date 2020/8/5 9:45
 */
public interface RpcServer {
    void start();

    <T> void publishService(T service, String serviceName);

}
