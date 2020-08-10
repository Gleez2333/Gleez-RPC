package com.gleez.core.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.gleez.commom.enumeration.RpcError;
import com.gleez.commom.exception.RpcException;
import com.gleez.core.loadbanlance.LoadBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author Gleez
 * @Date 2020/8/8 13:36
 */
public class NacosServiceDiscovery implements ServiceDiscovery {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    private static final String SERVER_PORT = "127.0.0.1:8848";
    private static final NamingService namingService;
    private final LoadBalance loadBalance;

    public NacosServiceDiscovery(LoadBalance loadBalance) {
        this.loadBalance = loadBalance;
    }

    static {
        try {
            namingService = NamingFactory.createNamingService(SERVER_PORT);
        } catch (NacosException e) {
            logger.error("连接到nacos时发生错误:", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = namingService.getAllInstances(serviceName);
            if(instances.size() == 0) {
                logger.error("找不到对应的服务: " + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
            Instance instance = loadBalance.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生：", e);
        }
        return null;
    }
}
