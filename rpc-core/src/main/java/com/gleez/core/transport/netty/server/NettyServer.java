package com.gleez.core.transport.netty.server;

import com.gleez.core.coder.CommonDecoder;
import com.gleez.core.coder.CommonEncoder;
import com.gleez.core.serializer.KryoSerializer;
import com.gleez.core.transport.api.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty服务提供侧
 * @Author Gleez
 * @Date 2020/8/5 12:46
 */
public class NettyServer implements RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Override
    public void start(int port) {
        // 设置eventLoopGroup线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // bootstrap配置netty
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 设置eventLoopGroup线程组
            serverBootstrap.group(bossGroup, workerGroup)
                    // 设置noiserver类型的channel
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 设置装配流水线
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new CommonEncoder(new KryoSerializer()));
                            pipeline.addLast(new CommonDecoder());
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            // 绑定端口，同步等待绑定成功
            ChannelFuture future = serverBootstrap.bind(port).sync();
            // 等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("启动服务时有错误发生: ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
