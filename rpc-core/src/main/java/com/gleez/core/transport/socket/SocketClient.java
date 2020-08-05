package com.gleez.core.transport.socket;

import com.gleez.commom.entity.RpcRequest;
import com.gleez.core.transport.api.RpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * @Author Gleez
 * @Date 2020/8/4 18:56
 * rpc客户端，封装RpcRequest请求发送到服务器端
 */
public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);
    private String host;
    private int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object sendRequest(RpcRequest request) {
        try(Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时发生错误"+e);
            return null;
        }
    }


}
