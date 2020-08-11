package com.gleez.core.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.gleez.commom.enumeration.RpcError;
import com.gleez.commom.exception.RpcException;
import com.gleez.core.registry.api.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * nacos服务注册
 * @Author Gleez
 * @Date 2020/8/7 15:05
 */
public class NacosServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    private String serverPort = "127.0.0.1:8848";
    private NamingService namingService;

    public NacosServiceRegistry(String serverPort) {
        this.serverPort = serverPort;
        try {
            namingService = NamingFactory.createNamingService(serverPort);
        } catch (NacosException e) {
            logger.error("连接到nacos时发生错误:", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    public NacosServiceRegistry() {
        try {
            namingService = NamingFactory.createNamingService(serverPort);
        } catch (NacosException e) {
            logger.error("连接到nacos时发生错误:", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }


    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        } catch (NacosException e) {
            logger.error("服务器注册时出现错误:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
