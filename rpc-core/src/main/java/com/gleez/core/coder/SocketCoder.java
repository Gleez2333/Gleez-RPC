package com.gleez.core.coder;

import com.gleez.commom.entity.RpcRequest;
import com.gleez.commom.entity.RpcResponse;
import com.gleez.commom.enumeration.PackageType;
import com.gleez.commom.enumeration.RpcError;
import com.gleez.commom.exception.RpcException;
import com.gleez.core.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @Author Gleez
 * @Date 2020/8/10 14:16
 */
public class SocketCoder {

    private final static Logger logger = LoggerFactory.getLogger(SocketCoder.class);
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    public static void write(OutputStream outputStream, Object object, CommonSerializer serializer) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeInt(MAGIC_NUMBER);
        if(object instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        out.writeInt(serializer.getCode());
        byte[] bytes = serializer.serialize(object);
        out.writeInt(bytes.length);
        out.write(bytes);
        out.flush();
    }


    public static Object read(InputStream inputStream) throws IOException {
        ObjectInputStream in = new ObjectInputStream(inputStream);
        int magic = in.readInt();
        if(magic != MAGIC_NUMBER) {
            logger.error("不识别协议包：{}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        int packetCode = in.readInt();
        Class<?> packetClass = null;
        if(packetCode == PackageType.REQUEST_PACK.getCode()) {
            packetClass = RpcRequest.class;
        } else if (packetCode == PackageType.RESPONSE_PACK.getCode()) {
            packetClass = RpcResponse.class;
        } else {
            logger.error("不识别数据包：{}", packetCode);
        }
        int serializeCode = in.readInt();
        CommonSerializer serializer = CommonSerializer.getByCode(serializeCode);
        if(serializer == null) {
            logger.error("不识别的反序列化器:{}", serializeCode);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readFully(bytes);
        Object result = serializer.deserialize(bytes, packetClass);
        return result;
    }


}
