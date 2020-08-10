package com.gleez.server;

import com.gleez.core.annotation.NacosConfig;
import com.gleez.core.annotation.ServiceScan;
import com.gleez.core.serializer.ProtobufSerializer;
import com.gleez.core.transport.api.RpcServer;
import com.gleez.core.transport.netty.server.NettyServer;

/**
 * @Author Gleez
 * @Date 2020/8/5 16:22
 */
@NacosConfig("127.0.0.1:8848")
@ServiceScan
public class TestNettyServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9998, new ProtobufSerializer());
        server.start();
    }
}
