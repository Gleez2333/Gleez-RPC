package com.gleez.core.registry.zookeeper;

import com.gleez.commom.enumeration.RpcError;
import com.gleez.commom.exception.RpcException;
import com.gleez.core.loadbanlance.LoadBalance;
import com.gleez.core.registry.api.ServiceDiscovery;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * zookeeper服务发现
 * @Author Gleez
 * @Date 2020/8/16 18:49
 */
public class ZookeeperServiceDiscovery implements ServiceDiscovery {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperServiceDiscovery.class);
    private static final int BASE_SLEEP_TIME = 1000;
    private static final int MAX_RETRIES = 3;
    private static final String PATH = "/gleez/";
    private String serverPort = "127.0.0.1:8848";
    private LoadBalance loadBalance;
    private CuratorFramework zkClient;
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);

    public ZookeeperServiceDiscovery(String serverPort, LoadBalance loadBalance) {
        this.serverPort = serverPort;
        this.loadBalance = loadBalance;
        zkClient = CuratorFrameworkFactory.builder()
                .connectString(serverPort)
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
    }


    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<String> instances = zkClient.getChildren().forPath(PATH+serviceName);
            if (instances.size() == 0) {
                logger.error("找不到对应的服务: " + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
            String select[] = ((String) loadBalance.select(instances)).split(":");
            return new InetSocketAddress(select[0], new Integer(select[1]));
        } catch (Exception e) {
            logger.error("获取服务时有错误发生：", e);
        }

        return null;
    }
}
