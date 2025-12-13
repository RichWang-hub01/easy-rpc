package com.wx.example.common.model;

import java.io.Serializable;
/**
 * @author WX
 * @date 2025-12-13 02:02
 * 用户实体类
 **/

public class User implements Serializable {

    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String toString() {
        return "User{name = " + name + "}";
    }
}

