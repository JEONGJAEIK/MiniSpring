package com.createspring.spring.event;

import java.lang.reflect.Method;

/**
 * 트랜잭셔널 이벤트리스너 래핑 어댑터
 */
public class TransactionListenerMethodAdapter {

    /**
     * 리스너 객체의 빈 네임
     */
    private String beanName;

    /**
     * 리스너가 실행해야할 메서드
     */
    private Method method;

    public TransactionListenerMethodAdapter(String beanName, Method method) {
        this.beanName = beanName;
        this.method = method;
    }

    public String getBeanName() {
        return beanName;
    }

    public Method getMethod() {
        return method;
    }
}
