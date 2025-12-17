package com.wx.easyrpc.proxy;

import com.wx.easyrpc.config.RpcConfig;
import com.wx.easyrpc.utils.ConfigUtils;

import java.lang.reflect.Proxy;

/**
*@author WX
*@date 2025-12-14 18:41
*@description 服务代理工厂 （用于创建代理对象）
**/
public class ServiceProxyFactory {
    /**
     * 创建代理对象
     * @param interfaceClass 接口类
     * @param <T>
     * @return
     */
    public static <T> T getProxy(Class<T> interfaceClass) {
        // 1.先读取rpcConfig,是否打开mock开关
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        // 2.开启则创建MockServiceProxy
        if ("true".equals(rpcConfig.getMockConfig())){
            return (T) Proxy.newProxyInstance(
                    interfaceClass.getClassLoader(),
                    new Class[]{interfaceClass},
                    new MockServiceProxy()
            );
        }
        // 3.否则创建ServiceProxy
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new ServiceProxy()
        );
    }

    /**
     * 执行流程如下：
     * 调用ServiceProxyFactory.getProxy(UserService.class)创建UserService接口的代理对象
     * 调用userService.getUser(user)时，实际调用的是代理对象的方法
     * JVM检测到这是代理对象，将调用转发给ServiceProxy的invoke方法
     * invoke方法构建RpcRequest对象，包含：
     * serviceName: "com.wx.example.common.service.UserService"
     * methodName: "getUser"
     * parameterTypes: [User.class]
     * args: [user对象]
     * 将RpcRequest序列化并通过HTTP发送到服务端
     * 服务端处理请求，执行实际的getUser方法，并返回结果
     * 客户端接收响应，反序列化得到结果并返回给调用方
     * 这种方式的优势在于完全解耦了客户端和服务端，客户端只需要持有接口即可调用远程服务，无需关心具体的实现细节和网络通信过程。
     */
}
