package com.createspring.spring.event;

import com.createspring.spring.transaction.TransactionPhase;

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

    /**
     * 리스너의 트랜잭션 페이즈
     */
    private TransactionPhase phase;

    public TransactionListenerMethodAdapter(String beanName, Method method, TransactionPhase phase) {
        this.beanName = beanName;
        this.method = method;
        this.phase = phase;
    }

    public String getBeanName() {
        return beanName;
    }

    public Method getMethod() {
        return method;
    }

    public TransactionPhase getPhase() {
        return phase;
    }

}
