package com.wx.easyrpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author WX
 * @date 2025-12-17 20:57
 * @description
 **/
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1.获取方法的返回值类型
        Class<?> returnType = method.getReturnType();
        log.info("mock invoke {}", method.getName());
        // 2.根据返回值类型获取对应的mock值
        Object mockValue = getMockValue(returnType);

        return mockValue;
    }

    /**
     * 获取mock值
     * @param returnType
     * @return
     */
    private Object getMockValue(Class<?> returnType) {
        // 1.判断返回值类型是否为基本类型
        if (returnType.isPrimitive()) {
            // 2.如果是基本类型，则返回对应的默认值
            if (returnType == boolean.class) {
                return false;
            } else if (returnType == byte.class) {
                return (byte) 0;
            } else if (returnType == short.class) {
                return (short) 0;
            } else if (returnType == int.class) {
                return 0;
            } else if (returnType == long.class) {
                return 0L;
            } else if (returnType == float.class) {
                return 0.0f;
            } else if (returnType == double.class) {
                return 0.0d;
            } else if (returnType == char.class) {
                return '\u0000';
            }
        }
        // 3.如果是对象类型，则返回null
        return null;
    }
}
