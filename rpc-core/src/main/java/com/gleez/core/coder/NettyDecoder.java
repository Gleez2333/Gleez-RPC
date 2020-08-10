package com.gleez.core.coder;

import com.gleez.commom.entity.RpcRequest;
import com.gleez.commom.entity.RpcResponse;
import com.gleez.commom.enumeration.PackageType;
import com.gleez.commom.enumeration.RpcError;
import com.gleez.commom.exception.RpcException;
import com.gleez.core.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author Gleez
 * @Date 2020/8/5 14:16
 */
public class NettyDecoder extends ReplayingDecoder {

    private static final Logger logger = LoggerFactory.getLogger(NettyDecoder.class);
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        int magic = in.readInt();
        if(magic != MAGIC_NUMBER) {
            logger.error("不识别的协议包:{}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        int packetCode = in.readInt();
        Class<?> packetClass;
        if (packetCode == PackageType.REQUEST_PACK.getCode()) {
            packetClass = RpcRequest.class;
        } else if(packetCode == PackageType.RESPONSE_PACK.getCode()) {
            packetClass = RpcResponse.class;
        }else {
            logger.error("不识别的数据包:{}", packetCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        int serializerCode = in.readInt();
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
        if(serializer == null) {
            logger.error("不识别的反序列化器:{}", serializerCode);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Object object = serializer.deserialize(bytes, packetClass);
        out.add(object);
    }
}
