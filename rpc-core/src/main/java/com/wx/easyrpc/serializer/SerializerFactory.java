package com.wx.easyrpc.serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WX
 * @date 2025-12-23 00:41
 * @description
 **/
public class SerializerFactory {
   /* private static final Map<String,Serializer> PropertySerializerMap = new HashMap<String,Serializer>(){
        {
            put(HessianSerializer.class.getName(),new HessianSerializer());
            put(JsonSerializer.class.getName(),new JsonSerializer());
            put(KryoSerializer.class.getName(),new KryoSerializer());
            put(JdkSerializer.class.getName(),new JdkSerializer());
        }
    };*/
    // 创建不可变Map，将序列化器存入
    private static final Map<String,Serializer> KEY_SERIALIZER_MAP = new HashMap<>();
    static {
        KEY_SERIALIZER_MAP.put(HessianSerializer.class.getName(), new HessianSerializer());
        KEY_SERIALIZER_MAP.put(JsonSerializer.class.getName(), new JsonSerializer());
        KEY_SERIALIZER_MAP.put(KryoSerializer.class.getName(), new KryoSerializer());
        KEY_SERIALIZER_MAP.put(JdkSerializer.class.getName(), new JdkSerializer());
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = KEY_SERIALIZER_MAP.get(SerializerKeys.JDK);

    /**
     * 获取自定义序列化器
     * @param serializerName
     * @return
     */
    public static Serializer getSerializer(String serializerName){
        Serializer serializer = KEY_SERIALIZER_MAP.getOrDefault(serializerName,DEFAULT_SERIALIZER);
        return serializer;
    }
}
