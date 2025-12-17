package com.wx.example;

import com.wx.easyrpc.config.RpcConfig;
import com.wx.easyrpc.proxy.ServiceProxyFactory;
import com.wx.easyrpc.utils.ConfigUtils;
import com.wx.example.common.model.User;
import com.wx.example.common.service.UserService;

/**
 * @author WX
 * @date 2025-12-13 02:11
 * 服务的测试消费类
 **/
public class EasyConsumerExample {
    public static void main(String[] args) {
        // 加载配置
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");

        User user = new User("zhangsan");

        // 创建服务,先使用静态代理
        //UserServiceProxy userServiceProxy = new UserServiceProxy();

        // 使用动态代理 创建服务
        // 此处userService是公共服务中的接口，类似于公司项目中的jar包依赖
        UserService userServiceProxy = ServiceProxyFactory.getProxy(UserService.class);
        // 通过代理对象调用服务，
        User newUser = userServiceProxy.getUser(user);

        if (newUser != null){
            System.out.println("用户名" + newUser.getName());
        }else {
            System.out.println("user == null");
        }
    }
}
