package com.gleez.core.transport.socket;

import com.gleez.commom.entity.RpcRequest;
import com.gleez.commom.entity.RpcResponse;
import com.gleez.commom.enumeration.ResponseCode;
import com.gleez.core.coder.SocketCoder;
import com.gleez.core.handler.RequestHandler;
import com.gleez.core.registry.api.ServiceRegistry;
import com.gleez.core.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @Author Gleez
 * @Date 2020/8/5 8:52
 */
public class RequestHandlerThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);
    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;
    private CommonSerializer serializer;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
       // this.serviceRegistry = serviceRegistry;
        this.serializer = serializer;
    }

    @Override
    public void run() {

        try {
            Object read = SocketCoder.read(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) read;
            Object result = requestHandler.handle(rpcRequest);
            SocketCoder.write(socket.getOutputStream(), RpcResponse.success(result, ResponseCode.SUCCESS), serializer);
        } catch (IOException e) {
            logger.error("调用或发送时有错误发生：", e);
        }

    }
}
