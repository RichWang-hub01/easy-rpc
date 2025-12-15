package com.wx.easyrpc;

import com.wx.easyrpc.config.RpcConfig;
import com.wx.easyrpc.constant.RpcConstant;
import com.wx.easyrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WX
 * @date 2025-12-15 22:36
 * @description RPC框架应用
 * 相当于 holder，存放了项目全局用到的变量。双检锁单例模式实现
 **/
@Slf4j
public class RpcApplication {
    /**
     * RPC 配置
     */
    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持传入自定义配置
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig){
        rpcConfig = newRpcConfig;
        log.info("初始化配置文件成功: {}", rpcConfig.toString());
    }

    /**
     * 初始化
     */
    public static void init(){
        // 1.初始化一个config
        RpcConfig newRpcConfig;
        // 2.调用工具类获取config
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("初始化配置文件失败");
            // 配置初始化失败
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取RpcConfig
     * @return
     */
    public static RpcConfig getRpcConfig() {
        // 第一次检查：避免不必要的同步
        if (rpcConfig == null) {
            // 同步块：确保只有一个线程能进入初始化代码
            synchronized (RpcApplication.class) {
                // 第二次检查：确保只初始化一次
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}

