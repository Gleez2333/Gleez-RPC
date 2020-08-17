package com.gleez.core.serializer;

import java.io.Serializable;

/**
 * 序列化接口
 * @Author Gleez
 * @Date 2020/8/5 14:24
 */
public interface CommonSerializer extends Serializable {
    byte[] serialize(Object object);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            case 2:
                return new HessianSerializer();
            case 3:
                return new ProtobufSerializer();
            default:
                return null;
        }
    }
}
