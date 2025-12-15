package com.wx.example;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.wx.easyrpc.model.RpcRequest;
import com.wx.easyrpc.model.RpcResponse;
import com.wx.easyrpc.serializer.JdkSerializer;
import com.wx.easyrpc.serializer.Serializer;
import com.wx.example.common.model.User;
import com.wx.example.common.service.UserService;

/**
 * @author WX
 * @date 2025-12-14 14:56
 * userService的代理类，封装了调用逻辑、
 * 此处逻辑不能复用，后续改为动态代理
 * 否则每个代理类都需要重新实现
 **/
public class UserServiceProxy implements UserService {
    @Override
    public User getUser(User user) {
        // 构建请求，去调用UserService的服务
        // 1.创建RpcRequest
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class<?>[]{User.class})
                .args(new Object[]{user}).build();
        // 2.指定序列化器
        Serializer serialize = new JdkSerializer();
        // 2.将请求序列化
        try {
            byte[] requestBytes = serialize.serialize(rpcRequest);
            byte[] result;
            // 3.发送请求
            HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(requestBytes)
                    .execute();
            // 4.构建响应
            result = httpResponse.bodyBytes();
            RpcResponse rpcResponse = serialize.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
