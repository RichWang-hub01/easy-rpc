package com.wx.easyrpc.model;

import com.wx.easyrpc.serializer.Serializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author WX
 * @date 2025-12-13 15:29
 * RPC响应
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse implements Serializable {
    /**
     * 响应数据
     */
    private Object data;

    /**
     * 响应数据类型（预留）
     * private Class<?> dataType;
     * */

    /**
     * 响应信息
     */
    private String message;

    /**
     * 异常信息
     */
    private Exception exception;
}