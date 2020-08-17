package com.gleez.core.registry.zookeeper;

import com.gleez.commom.enumeration.RpcError;
import com.gleez.commom.exception.RpcException;
import com.gleez.core.registry.api.ServiceRegistry;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * zookeeper服务注册
 * @Author Gleez
 * @Date 2020/8/16 18:49
 */
public class ZookeeperServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperServiceRegistry.class);
    private static final String DEFAULT_ADDRESS = "127.0.0.1:2181";
    private String serverPort = "127.0.0.1:2181";
    private static final int BASE_SLEEP_TIME = 1000;
    private static final int MAX_RETRIES = 3;
    private CuratorFramework zkClient;
    private static final String PATH = "/gleez/";
    // Retry strategy. Retry 3 times, and will increase the sleep time between retries.
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);

    public ZookeeperServiceRegistry(String serverPort) {
        this.serverPort = serverPort;
        zkClient = CuratorFrameworkFactory.builder()
                // the server to connect to (can be a server list)
                .connectString(serverPort)
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            zkClient.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL).
                    forPath(PATH+serviceName+"/"+inetSocketAddress.getHostString()+":"+inetSocketAddress.getPort());
        } catch (Exception e) {
            logger.error("服务器注册时出现错误:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }

    }
}
