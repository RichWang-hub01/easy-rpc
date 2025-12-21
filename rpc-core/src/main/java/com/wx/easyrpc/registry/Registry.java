package com.wx.easyrpc.registry;

import com.wx.easyrpc.config.RegistryConfig;
import com.wx.easyrpc.model.ServiceMetaInfo;
import dev.failsafe.Execution;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author WX
 * @date 2025-12-20 00:35
 * @description 注册中心相关接口
 **/
public interface Registry {
    /**
     * 注册中心初始化
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 服务注册（服务端）
     * @param serviceMetaInfo
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 服务下线（服务端）
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo) ;

    /**
     * 服务发现（消费端），获取服务的所有节点
     * @param serviceKey
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 注册中心销毁
     */
    void destroy();



}

