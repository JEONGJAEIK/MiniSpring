package com.createspring.spring.bean;

/**
 * 빈 저장소 인터페이스
 */
public interface ApplicationContext {

    /**
     * 빈 객체 반환
     */
    Object getBean(String beanName);

    String getBeanName(Class<?> clazz);
}
