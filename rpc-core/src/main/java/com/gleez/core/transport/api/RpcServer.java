package com.gleez.core.transport.api;

import com.gleez.core.serializer.CommonSerializer;

/**
 * @Author Gleez
 * @Date 2020/8/5 9:45
 */
public interface RpcServer {
    void start();

    <T> void publishService(T service, String serviceName);

    void setSerializer(CommonSerializer serializer);

}
