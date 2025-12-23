package com.wx.easyrpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.wx.easyrpc.RpcApplication;
import com.wx.easyrpc.model.RpcRequest;
import com.wx.easyrpc.model.RpcResponse;
import com.wx.easyrpc.serializer.JdkSerializer;
import com.wx.easyrpc.serializer.Serializer;
import com.wx.easyrpc.serializer.SerializerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author WX
 * @date 2025-12-14 18:17
 * @description 服务代理类
 **/
public class ServiceProxy implements InvocationHandler {
    /**
     * 代理逻辑
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // 1.创建RpcRequest
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args).build();
        // 获取系统配置的序列化器
        Serializer serializer = SerializerFactory.getSerializer(RpcApplication.getRpcConfig().getSerializer());
        // 2.将请求序列化
        try {
            byte[] requestBytes = serializer.serialize(rpcRequest);
            byte[] result;
            // 3.发送请求
            // todo 此处服务地址硬编码，后续使用服务注册中心
            HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(requestBytes)
                    .execute();
            // 4.构建响应
            result = httpResponse.bodyBytes();
            // 5.反序列化响应
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
