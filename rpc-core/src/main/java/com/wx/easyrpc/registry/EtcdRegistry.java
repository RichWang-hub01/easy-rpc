package com.wx.easyrpc.registry;

import cn.hutool.json.JSONUtil;
import com.wx.easyrpc.config.RegistryConfig;
import com.wx.easyrpc.model.ServiceMetaInfo;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WX
 * @date 2025-12-21 11:01
 * @description
 **/
@Slf4j
public class EtcdRegistry implements Registry{
    /**
     * etcd客户端
     */
    private  Client client;

    /**
     * etcd的键值存储客户端
     */
    private  KV kvClient;

    /**
     * etcd的根路径
     */
    private static final String ETCD_ROOT_PATH = "/rpc/";

    @Override
    public void init(RegistryConfig registryConfig) {
        // 初始化etcd的客户端，设置连接超时时间为10秒
        client = Client.builder()
                .endpoints(registryConfig.getAddress())
                .connectTimeout(Duration.ofMillis(registryConfig.getTimeout()))
                .build();

        kvClient = client.getKVClient();
    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {
        // 创建lease客户端
        Lease leaseClient = client.getLeaseClient();

        // 创建租约，设置租约时间为30秒，后续需要维持心跳包进行续期，否则服务会自动下线
        long leaseId = leaseClient.grant(30).get().getID();

        //构造注册键值对
        String registerKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
        ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);

        // 将键值对与租约关联起来
        PutOption putOption = PutOption.builder()
                .withLeaseId(leaseId)
                .build();

        kvClient.put(key,value,putOption).get();

    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        // 将服务信息从etcd中删除
        ByteSequence key = ByteSequence.from(ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey(), StandardCharsets.UTF_8);
        // todo 这里如果不能删除添加join或者get
        kvClient.delete(key);
    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        // 从etcd中获取服务信息
        // 根据serviceKey获取服务，可能会有多个服务
        // 根据前缀搜索
        String searchPrefixKey = ETCD_ROOT_PATH + serviceKey + "/";

        GetOption getOption = GetOption.builder()
                .isPrefix(true)
                .build();
        try {
            List<KeyValue> keyValues = kvClient.get(
                            ByteSequence.from(searchPrefixKey, StandardCharsets.UTF_8), getOption)
                    .get()
                    .getKvs();
            List<ServiceMetaInfo> serviceMetaInfos = keyValues.stream().map(keyValue -> {
                String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                return JSONUtil.toBean(value, ServiceMetaInfo.class);
            }).collect(Collectors.toList());
            // 返回服务信息列表
            return serviceMetaInfos;
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败", e);
        }
    }

    @Override
    public void destroy() {
        // 销毁etcd的连接
        log.info("etcd注册中心下线");
        if (kvClient != null){
            kvClient.close();
        }
        if (client != null){
            client.close();
        }
    }
}
