package com.wx.example.provider;

import com.wx.easyrpc.RpcApplication;
import com.wx.easyrpc.registry.LocalRegistry;
import com.wx.easyrpc.server.VertxHttpServer;
import com.wx.example.common.service.UserService;

/**
 * @author WX
 * @date 2025-12-13 02:10
 * 测试服务提供者
 **/
public class EasyProviderExample {
    public static void main(String[] args) {
        // 加载配置
        RpcApplication.init();

        // 注册服务,存储到本地
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动web服务
        VertxHttpServer vertxHttpServer = new VertxHttpServer();
        vertxHttpServer.doStart(8080);


    }
}
