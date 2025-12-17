package com.wx.easyrpc.config;

import lombok.Data;

/**
 * @author WX
 * @date 2025-12-15 22:25
 * @description RPC 框架配置
 **/

@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "easy-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * mock服务开关
     */
    private String mockConfig = "false";

}

