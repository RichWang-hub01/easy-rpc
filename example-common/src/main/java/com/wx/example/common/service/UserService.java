package com.wx.example.common.service;
import com.wx.example.common.model.User;

/**
 * @author WX
 * @date 2025-12-13 02:04
 * 用户服务类
 **/

public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);
}
