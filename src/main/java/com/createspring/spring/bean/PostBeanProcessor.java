package com.createspring.spring.bean;

import com.createspring.spring.annotation.Transactional;
import com.createspring.spring.proxy.ProxyFactory;

import java.lang.reflect.Method;

/**
 * 빈 후처리기
 */
public class PostBeanProcessor {
    private final ProxyFactory proxyFactory;

    public PostBeanProcessor(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    /**
     * 트랜잭셔널이 달린 클래스와 메서드를 판별하여 프록시팩토리로 전달한다.
     */
    public Object scanTargetProxy(Object o, Class<?> clazz) {
        if (clazz.isAnnotationPresent(Transactional.class) || hasTransactionalMethod(clazz)) {
            return proxyFactory.handleInterceptor(o);
        }
        return o;
    }

    /**
     * 트랜잭셔널이 달린 메서드를 판별한다.
     */
    private boolean hasTransactionalMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Transactional.class)) {
                return true;
            }
        }
        return false;
    }
}
