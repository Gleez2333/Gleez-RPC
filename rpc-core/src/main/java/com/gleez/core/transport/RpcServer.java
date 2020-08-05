package com.gleez.core.transport;

import com.gleez.core.handler.RequestHandler;
import com.gleez.core.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:23
 */
public class RpcServer {
    private final ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLACKING_QUEUE_CAPACITY = 100;
    private final ServiceRegistry serviceRegistry;
    private RequestHandler requestHandler = new RequestHandler();

    public RpcServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLACKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workingQueue, threadFactory);
    }



    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器正在启动。。。。。");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("服务端连接：{}：{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serviceRegistry));
            }
        } catch (IOException e) {
            logger.error("连接时有错误发生：", e);
        } finally {
            threadPool.shutdown();
        }
    }
}

