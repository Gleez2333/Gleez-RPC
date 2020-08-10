package com.gleez.core.transport.netty.client;

import com.gleez.commom.entity.RpcRequest;
import com.gleez.commom.entity.RpcResponse;
import com.gleez.core.coder.NettyDecoder;
import com.gleez.core.coder.NettyEncoder;
import com.gleez.core.loadbanlance.LoadBalance;
import com.gleez.core.loadbanlance.RoundRobinLoadBalance;
import com.gleez.core.registry.NacosServiceDiscovery;
import com.gleez.core.registry.ServiceDiscovery;
import com.gleez.core.serializer.CommonSerializer;
import com.gleez.core.serializer.KryoSerializer;
import com.gleez.core.transport.api.RpcClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @Author Gleez
 * @Date 2020/8/5 13:42
 */
public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private final Bootstrap bootstrap;
    private final ServiceDiscovery serviceDiscovery;
    private CommonSerializer serializer;

    public NettyClient() {
        this(new KryoSerializer(), new RoundRobinLoadBalance());
    }

    public NettyClient(CommonSerializer serializer) {
        this(serializer, new RoundRobinLoadBalance());
    }

    public NettyClient(LoadBalance loadBalance) {
        this(new KryoSerializer(), loadBalance);
    }

    public NettyClient(CommonSerializer serializer, LoadBalance loadBalance) {
        this.serializer = serializer;
        this.serviceDiscovery = new NacosServiceDiscovery(loadBalance);
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new NettyDecoder())
                                .addLast(new NettyEncoder(serializer))
                                .addLast(new NettyClientHandler());
                    }
                });
    }


    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
            ChannelFuture future = bootstrap.connect(inetSocketAddress.getHostName(), inetSocketAddress.getPort()).sync();
            logger.info("客户端连接到服务器端 :", inetSocketAddress.getAddress());
            Channel channel = future.channel();
            if (channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        logger.info(String.format("客户端发送消息：%s", rpcRequest.toString()));
                    }else {
                        logger.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
             //   return rpcResponse.getData();
                return rpcResponse;
            }
        } catch (InterruptedException e) {
            logger.error("发送消息时出错:", e);
        }
        return null;
    }

}
