package com.wx.example;

import com.wx.example.common.model.User;
import com.wx.example.common.service.UserService;

/**
 * @author WX
 * @date 2025-12-13 02:11
 * 服务的测试消费类
 **/
public class EasyConsumerExample {
    public static void main(String[] args) {
        User user = new User("zhangsan");

        // 创建服务,先使用静态代理
        UserServiceProxy userServiceProxy = new UserServiceProxy();
        // 调用服务
        User newUser = userServiceProxy.getUser(user);

        if (newUser != null){
            System.out.println("用户名" + newUser.getName());
        }else {
            System.out.println("user == null");
        }
    }
}
