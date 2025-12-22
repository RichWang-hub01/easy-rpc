package com.wx.easyrpc.serializer;


import java.util.HashMap;
import java.util.Map;

/**
 * @author WX
 * @date 2025-12-23 00:41
 * @description
 **/
public class SerializerFactory {
    // 创建不可变Map，将序列化器存入
   /* private static final Map<String,Serializer> PropertySerializerMap = new HashMap<String,Serializer>(){
        {
            put(HessianSerializer.class.getName(),new HessianSerializer());
            put(JsonSerializer.class.getName(),new JsonSerializer());
            put(KryoSerializer.class.getName(),new KryoSerializer());
            put(JdkSerializer.class.getName(),new JdkSerializer());
        }
    };*/

    private static final Map<String,Serializer> PropertySerializerMap = new HashMap<>();
    static {
        PropertySerializerMap.put(HessianSerializer.class.getName(), new HessianSerializer());
        PropertySerializerMap.put(JsonSerializer.class.getName(), new JsonSerializer());
        PropertySerializerMap.put(KryoSerializer.class.getName(), new KryoSerializer());
        PropertySerializerMap.put(JdkSerializer.class.getName(), new JdkSerializer());
    }



    /**
     * 获取自定义序列化器
     * @param serializerName
     * @return
     */
    public static Serializer getSerializer(String serializerName){
        Serializer serializer = PropertySerializerMap.get(serializerName);
        if (serializer == null){
            throw new RuntimeException("未找到对应的序列化器");
        }
        return serializer;
    }
}
