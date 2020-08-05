package com.gleez.core.transport;

import com.gleez.commom.entity.RpcRequest;
import com.gleez.commom.entity.RpcResponse;
import com.gleez.commom.enumeration.ResponseCode;
import com.gleez.core.handler.RequestHandler;
import com.gleez.core.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author Gleez
 * @Date 2020/8/5 8:52
 */
public class SocketRequestHandlerThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SocketRequestHandlerThread.class);
    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    public SocketRequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
             RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
             String interfaceName = rpcRequest.getInterfaceName();
             Object service = serviceRegistry.getService(interfaceName);
             Object result = requestHandler.handle(rpcRequest, service);
             objectOutputStream.writeObject(RpcResponse.success(result, ResponseCode.SUCCESS));
             objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException  e) {
            logger.error("调用或发送时有错误发生：", e);
        }
    }
}
