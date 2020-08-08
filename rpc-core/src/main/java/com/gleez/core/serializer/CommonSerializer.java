package com.gleez.core.serializer;

/**
 * 序列化接口
 * @Author Gleez
 * @Date 2020/8/5 14:24
 */
public interface CommonSerializer {
    byte[] serialize(Object object);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    Integer KRYO_SERIALIZER = 0;
    Integer JSON_SERIALIZER = 1;

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
