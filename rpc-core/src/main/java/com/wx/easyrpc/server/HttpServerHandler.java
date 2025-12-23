package com.wx.easyrpc.server;

import com.wx.easyrpc.RpcApplication;
import com.wx.easyrpc.model.RpcRequest;
import com.wx.easyrpc.model.RpcResponse;
import com.wx.easyrpc.registry.LocalRegistry;
import com.wx.easyrpc.serializer.JdkSerializer;
import com.wx.easyrpc.serializer.Serializer;
import com.wx.easyrpc.serializer.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author WX
 * @date 2025-12-13 15:33
 * http服务器请求处理类
 **/
@Slf4j
public class HttpServerHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest request) {
        // 获取系统配置的序列化器
        Serializer serializer = SerializerFactory.getSerializer(RpcApplication.getRpcConfig().getSerializer());
        // 记录日志
        log.info("接收到请求: {},方法名称: {}", request.uri(), request.method());
        // 异步处理请求
        request.bodyHandler(new Handler<Buffer>() {
            @Override
            public void handle(Buffer buffer) {
                // 取出序列化后的请求参数字节数组
                byte[] bytes = buffer.getBytes();
                // 反序列化请求参数
                RpcRequest rpcRequest = null; // 在try块外声明变量
                // 反序列化请求参数
                try {
                    rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.info("请求参数: {}", rpcRequest);

                // 创建响应结果
                RpcResponse rpcResponse = new RpcResponse();
                if (rpcRequest == null){
                    rpcResponse.setMessage("请求数据为空");
                }
                // 获取要调用的服务实现类，通过反射调用
                Class<?> serviceImplClass = LocalRegistry.get(rpcRequest.getServiceName());
                try {
                    Method method = serviceImplClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                    // 参数1: 服务实现类，参数2: 请求参数
                    Object result = method.invoke(serviceImplClass.newInstance(), rpcRequest.getArgs());

                    // 设置响应结果
                    rpcResponse.setData(result);
                    rpcResponse.setMessage("请求成功");
                    rpcResponse.setDataType(method.getReturnType());

                    doResponse(request, rpcResponse, serializer);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer){
        // 设置响应头
        HttpServerResponse httpServerResponse = request.response()
                .putHeader("content-type", "application/json");
        try {
            // 序列化响应结果
            byte[] serialize = serializer.serialize(rpcResponse);
            // 设置响应体
            httpServerResponse.end(Buffer.buffer(serialize));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end("请求失败");
        }

    }

}
