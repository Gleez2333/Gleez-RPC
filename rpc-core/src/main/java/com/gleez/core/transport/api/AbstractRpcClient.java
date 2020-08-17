package com.gleez.core.transport.api;

import com.gleez.commom.Utils.ReflectUtil;
import com.gleez.commom.enumeration.RegistryType;
import com.gleez.commom.enumeration.RpcError;
import com.gleez.commom.exception.RpcException;
import com.gleez.core.annotation.RegistryConfig;
import com.gleez.core.loadbanlance.LoadBalance;
import com.gleez.core.registry.api.ServiceDiscovery;
import com.gleez.core.registry.nacos.NacosServiceDiscovery;
import com.gleez.core.registry.zookeeper.ZookeeperServiceDiscovery;
import com.gleez.core.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象RPC客户端
 * @Author Gleez
 * @Date 2020/8/10 17:47
 */
public abstract class AbstractRpcClient implements RpcClient {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected ServiceDiscovery serviceDiscovery;
    protected CommonSerializer serializer;

    public void scan(LoadBalance loadBalance) {
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;
        try {
            startClass = Class.forName(mainClassName);
            if (!startClass.isAnnotationPresent(RegistryConfig.class)) {
                logger.error("缺少服务中心配置 @RegistryConfig 注解");
                throw new RpcException(RpcError.REGISTRY_CONFIG_NOT_FOUND);
            } else {
                RegistryType type = startClass.getAnnotation(RegistryConfig.class).type();
                String address = startClass.getAnnotation(RegistryConfig.class).address();
                if (type == RegistryType.NACOS) {
                    serviceDiscovery = new NacosServiceDiscovery(address, loadBalance);
                } else if (type == RegistryType.ZOOKEEPER) {
                    serviceDiscovery = new ZookeeperServiceDiscovery(address, loadBalance);
                } else {
                    logger.error("该服务中心不存在");
                    throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
                }
            }


        } catch (ClassNotFoundException e) {
            logger.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }
    }

}
