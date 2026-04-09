package com.createspring.spring.bean;

/**
 * 빈 정의
 * 클래스 메타데이터를 관리한다.
 */
public class BeanDefinition {

    private final Class<?> beanClass;

    public BeanDefinition(Class<?> metaData) {
        this.beanClass = metaData;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }
}
