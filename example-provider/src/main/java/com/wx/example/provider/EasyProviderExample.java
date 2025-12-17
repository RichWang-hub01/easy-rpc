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
        /**
         * 当前存在的耦合问题
         * 服务提供者需要主动注册服务： 在EasyProviderExample中，服务提供者必须显式地调用LocalRegistry.register()来注册服务，这意味着服务提供者必须了解RPC框架的内部实现。
         * 服务提供者需要启动RPC服务器： 服务提供者还需要自己创建并启动HTTP服务器(VertxHttpServer)，这使得服务提供者与具体的RPC传输层实现紧密绑定。
         * 服务注册是本地的： 使用的是LocalRegistry，这意味着服务只能在同一JVM内调用，无法实现分布式调用。
         * 如何解耦服务提供者和RPC框架
         * 一个更好的设计应该是这样的：
         * 服务提供者只关注业务逻辑，不应该感知RPC框架的存在。
         * 服务自动注册：可以通过注解扫描或配置文件方式自动注册服务。
         * 框架层面启动服务：RPC框架应该负责启动服务和处理网络通信。
         *
         * // 服务实现类可以添加注解标识为RPC服务
         * @RpcService
         * public class UserServiceImpl implements UserService {
         *     @Override
         *     public User getUser(String name) {
         *         return new User(name, 18);
         *     }
         * }
         *
         * 然后RPC框架在启动时自动扫描带有@RpcService注解的类并注册它们。
         * 总的来说，你的观察非常准确。目前的实现更像是一个"半成品"，缺少了真正RPC框架应有的解耦特性和自动化能力。
         */
    }
}
