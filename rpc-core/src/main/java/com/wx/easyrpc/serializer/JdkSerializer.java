package com.wx.easyrpc.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author WX
 * @date 2025-12-13 14:32
 **/
public class JdkSerializer implements Serializer {
    /**
     * 序列化
     *
     * @param object
     * @param <T>
     * @return
     * @throws IOException
     */
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        // 创建字节数组输出流，用于存储序列化后的数据
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 创建对象输出流，用于将对象序列化到输出流中
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        // 将传入的对象写入到对象输出流中
        objectOutputStream.writeObject(object);
        // 关闭对象输出流，释放资源
        objectOutputStream.close();
        // 返回序列化后的字节数组
        return outputStream.toByteArray();
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        // 创建字节数组输入流，用于从字节数组中读取数据
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        // 创建对象输入流，用于从输入流中读取对象
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            // 从输入流中读取并反序列化对象
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭对象输入流释放资源
            objectInputStream.close();
        }

    }
}
