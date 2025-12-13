package com.wx.example.provider;

import com.wx.example.common.model.User;
import com.wx.example.common.service.UserService;

/**
 * @author WX
 * @date 2025-12-13 02:07
 **/
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名" + user.getName());
        return user;
    }
}
