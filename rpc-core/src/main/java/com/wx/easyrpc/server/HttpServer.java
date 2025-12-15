package com.wx.easyrpc.server;

/**
 * @author WX
 * @date 2025-12-13 02:35
 * http服务器接口
 **/
public interface HttpServer {

    /**
     * 启动服务器
     * @param port
     */
    void doStart(int port);
}
