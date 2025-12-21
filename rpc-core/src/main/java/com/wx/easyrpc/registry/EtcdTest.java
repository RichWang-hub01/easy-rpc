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
public class EtcdTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建客户端
        Client client = Client.builder().endpoints("http://127.0.0.1:2379")
                .build();

        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("test_key".getBytes());
        ByteSequence value = ByteSequence.from("test_value".getBytes());

        // 插入一个键值对
        kvClient.put(key, value).join();

        // 获取键值对
        CompletableFuture<GetResponse> future = kvClient.get(key);
        GetResponse response = future.get();
        System.out.println(response);

        System.out.println(kvClient.delete(key).get());

        /*
         * 这个`Client`接口是etcd客户端的核心接口，提供了与etcd服务器交互的各种方法。以下是各方法的详细说明：
         *
         * 1. `getAuthClient()`：获取认证客户端，用于处理etcd的认证相关操作，如用户管理、角色权限等。
         *
         * 2. `getKVClient()`：获取键值存储客户端，这是etcd最核心的功能，用于对键值对进行增删改查操作。
         *
         * 3. `getClusterClient()`：获取集群客户端，用于管理etcd集群相关的操作，比如获取集群成员信息等。
         *
         * 4. `getMaintenanceClient()`：获取维护客户端，用于执行etcd集群的维护操作，如备份、恢复等。
         *
         * 5. `getLeaseClient()`：获取租约客户端，etcd支持给键值对设置租约（lease），租约到期后键值对会自动删除。
         *
         * 6. `getWatchClient()`：获取监听客户端，用于监听键值对的变化，当被监听的键发生变化时可以收到通知。
         *
         * 7. `getLockClient()`：获取锁客户端，提供分布式锁功能。
         *
         * 8. `getElectionClient()`：获取选举客户端，用于实现领导者选举功能。
         *
         * 9. `close()`：关闭客户端连接，释放资源。
         *
         * 10. `builder()`：静态方法，返回一个`ClientBuilder`对象，用于构建`Client`实例。
         *
         * 这些方法共同构成了etcd Java客户端的主要功能，涵盖了etcd提供的各种分布式系统所需的基础服务。
         */
    }
}
