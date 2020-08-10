package com.gleez.core.transport.socket;

import com.gleez.commom.factory.ThreadPoolFactory;
import com.gleez.core.handler.RequestHandler;
import com.gleez.core.provider.ServiceProviderImpl;
import com.gleez.core.registry.NacosServiceRegistry;
import com.gleez.core.serializer.CommonSerializer;
import com.gleez.core.serializer.KryoSerializer;
import com.gleez.core.transport.api.AbstractRpcServer;
import com.gleez.core.transport.api.RpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:23
 */
public class SocketServer extends AbstractRpcServer {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private final ExecutorService threadPool;;
    private RequestHandler requestHandler = new RequestHandler();

    public SocketServer(String host, int port) {
        this(host, port, new KryoSerializer());
    }

    public SocketServer(String host, int port, CommonSerializer serializer) {
        this.host = host;
        this.port = port;
        this.serializer = serializer;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
        scanServices();
    }


    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器启动……");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }


}

