package com.gleez.core.transport.socket;

import com.gleez.commom.entity.RpcRequest;
import com.gleez.core.coder.SocketCoder;
import com.gleez.core.loadbanlance.LoadBalance;
import com.gleez.core.loadbanlance.RoundRobinLoadBalance;
import com.gleez.core.registry.NacosServiceDiscovery;
import com.gleez.core.registry.ServiceDiscovery;
import com.gleez.core.serializer.CommonSerializer;
import com.gleez.core.serializer.KryoSerializer;
import com.gleez.core.transport.api.RpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
        this(new KryoSerializer(), new RoundRobinLoadBalance());
    }

    public SocketClient(CommonSerializer serializer) {
        this(serializer, new RoundRobinLoadBalance());
    }

    public SocketClient(LoadBalance loadBalance) {
        this(new KryoSerializer(), loadBalance);
    }

    public SocketClient(CommonSerializer serializer, LoadBalance loadBalance) {
        this.serializer = serializer;
        this.serviceDiscovery = new NacosServiceDiscovery(loadBalance);
    }

    @Override
    public Object sendRequest(RpcRequest request) {
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(request.getInterfaceName());
        try(Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            SocketCoder.write(socket.getOutputStream(), request, serializer);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//            objectOutputStream.writeObject(request);
//            objectOutputStream.flush();
//            return objectInputStream.readObject();
            Object read = SocketCoder.read(socket.getInputStream());
            return read;
        } catch (IOException e) {
            logger.error("调用时发生错误"+e);
            return null;
        }
    }



}
