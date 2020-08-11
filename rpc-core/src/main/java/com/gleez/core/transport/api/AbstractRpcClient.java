package com.gleez.core.transport.api;

import com.gleez.commom.Utils.ReflectUtil;
import com.gleez.commom.enumeration.RpcError;
import com.gleez.commom.exception.RpcException;
import com.gleez.core.annotation.NacosConfig;
import com.gleez.core.registry.api.ServiceDiscovery;
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
    protected String nacosIp;

    public void scan() {
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;
        try {
            startClass = Class.forName(mainClassName);
            if(startClass.isAnnotationPresent(NacosConfig.class) && !"".equals(startClass.getAnnotation(NacosConfig.class).value())) {
                nacosIp = startClass.getAnnotation(NacosConfig.class).value();
            } else  {
                nacosIp = "127.0.0.1:8848";
            }
        } catch (ClassNotFoundException e) {
            logger.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }
    }

}
