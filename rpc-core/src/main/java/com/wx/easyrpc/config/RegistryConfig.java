package com.wx.easyrpc.config;
import lombok.Data;
/**
 * @author WX
 * @date 2025-12-20 00:28
 * @description 注册中心配置
 * 默认使用 Etcd 注册中心
 **/
@Data
public class RegistryConfig {
    /**
     * 注册中心类别
     */
    private String registry = "etcd";

    /**
     * 注册中心地址
     */
    private String address = "http://localhost:2379";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间（单位毫秒）
     */
    private Long timeout = 10000L;
}

