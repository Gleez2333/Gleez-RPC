package com.gleez.core.registry;

import com.gleez.commom.enumeration.RpcError;
import com.gleez.commom.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认服务注册类
 * @Author Gleez
 * @Date 2020/8/4 23:33
 */
public class DefaultServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final Set<String> registrySet = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized <T> void registry(T service) {
        String className = service.getClass().getCanonicalName();
        if (registrySet.contains(className)) return ;
        registrySet.add(className);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            // 如果接口数目为0，说明注册的类没有实现接口
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> anInterface : interfaces) {
            serviceMap.put(anInterface.getCanonicalName(), service);
        }
        logger.info("向接口：{} 注册服务： {}", interfaces, className);
    }

    @Override
    public synchronized Object getService(String serviceName) {
        Object o = serviceMap.get(serviceName);
        if (o == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return o;
    }
}
