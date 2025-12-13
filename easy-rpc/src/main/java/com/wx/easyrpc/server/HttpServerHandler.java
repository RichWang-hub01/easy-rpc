package com.wx.easyrpc.server;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;

/**
 * @author WX
 * @date 2025-12-13 15:33
 * http服务器请求处理类
 **/
public class HttpServerHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        // 指定序列化器
    }
}
