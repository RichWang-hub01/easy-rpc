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
        // 创建服务,先指定为null
        UserService userService = null;
        User user = new User("zhangsan");

        // 调用服务
        User newUser = userService.getUser(user);

        if (newUser != null){
            System.out.println("用户名" + newUser.getName());
        }else {
            System.out.println("user == null");
        }
    }
}
