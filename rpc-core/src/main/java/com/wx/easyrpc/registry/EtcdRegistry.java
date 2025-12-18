package com.wx.easyrpc.registry;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author WX
 * @date 2025-12-19 00:10
 * @description
 **/
public class EtcdRegistry {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建客户端
        Client client = Client.builder().endpoints("http://127.0.0.1:2379")
                .build();

        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("test_kry".getBytes());
        ByteSequence value = ByteSequence.from("test_value".getBytes());

        // 插入一个键值对
        kvClient.put(key, value).join();

        // 获取键值对
        CompletableFuture<GetResponse> future = kvClient.get(key);
        GetResponse response = future.get();
        System.out.println(response);
    }
}
