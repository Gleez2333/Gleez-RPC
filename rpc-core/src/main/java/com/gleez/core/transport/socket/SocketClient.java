package com.gleez.core.transport.socket;

import com.gleez.commom.entity.RpcRequest;
import com.gleez.commom.enumeration.RpcError;
import com.gleez.commom.exception.RpcException;
import com.gleez.core.loadbanlance.LoadBalance;
import com.gleez.core.registry.NacosServiceDiscovery;
import com.gleez.core.registry.ServiceDiscovery;
import com.gleez.core.serializer.CommonSerializer;
import com.gleez.core.transport.api.RpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * @Author Gleez
 * @Date 2020/8/4 18:56
 * rpc客户端，封装RpcRequest请求发送到服务器端
 */
public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);
    private final ServiceDiscovery serviceDiscovery;
    private CommonSerializer serializer;

    public SocketClient() {
        this.serviceDiscovery = new NacosServiceDiscovery();
    }

    @Override
    public Object sendRequest(RpcRequest request) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(request.getInterfaceName());
        try(Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
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

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void setLoadBalance(LoadBalance loadBalance) {
        this.serviceDiscovery.setLoadBalance(loadBalance);
    }


}
