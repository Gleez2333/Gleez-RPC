package com.gleez.core.transport.api;

import com.gleez.commom.Utils.ReflectUtil;
import com.gleez.commom.enumeration.RegistryType;
import com.gleez.commom.enumeration.RpcError;
import com.gleez.commom.exception.RpcException;
import com.gleez.core.annotation.RegistryConfig;
import com.gleez.core.annotation.Service;
import com.gleez.core.annotation.ServiceScan;
import com.gleez.core.provider.ServiceProvider;
import com.gleez.core.registry.api.ServiceRegistry;
import com.gleez.core.registry.nacos.NacosServiceRegistry;
import com.gleez.core.registry.zookeeper.ZookeeperServiceRegistry;
import com.gleez.core.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * 抽象RPC服务端
 * @Author Gleez
 * @Date 2020/8/8 14:50
 */
public abstract class AbstractRpcServer implements RpcServer{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String host;
    protected int port;
    protected CommonSerializer serializer;


    protected ServiceRegistry serviceRegistry;
    protected ServiceProvider serviceProvider;

    public void scanServices() {
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;
        try {
            startClass = Class.forName(mainClassName);
            if(!startClass.isAnnotationPresent(ServiceScan.class)) {
                logger.error("启动类缺少 @ServiceScan 注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
            if (!startClass.isAnnotationPresent(RegistryConfig.class)) {
                logger.error("缺少服务中心配置 @RegistryConfig 注解");
                throw new RpcException(RpcError.REGISTRY_CONFIG_NOT_FOUND);
            } else {
                RegistryType type = startClass.getAnnotation(RegistryConfig.class).type();
                String address = startClass.getAnnotation(RegistryConfig.class).address();
                if (type == RegistryType.NACOS) {
                    serviceRegistry = new NacosServiceRegistry(address);
                } else if (type == RegistryType.ZOOKEEPER) {
                    serviceRegistry = new ZookeeperServiceRegistry(address);
                } else {
                    logger.error("该服务中心不存在");
                    throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }
        String basePackage = startClass.getAnnotation(ServiceScan.class).value();
        if("".equals(basePackage)) {
            basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));
        }
        Set<Class<?>> classSet = ReflectUtil.getClasses(basePackage);
        for(Class<?> clazz : classSet) {
            if(clazz.isAnnotationPresent(Service.class)) {
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object obj;
                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error("创建 " + clazz + " 时有错误发生");
                    continue;
                }
                if("".equals(serviceName)) {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> oneInterface: interfaces){
                        publishService(obj, oneInterface.getCanonicalName());
                    }
                } else {
                    publishService(obj, serviceName);
                }
            }
        }
    }

    @Override
    public <T> void publishService(T service, String serviceName) {
        serviceProvider.addServiceProvider(service, serviceName);
        serviceRegistry.register(serviceName, new InetSocketAddress(host, port));
    }



}
