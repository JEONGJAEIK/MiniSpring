package com.createspring.spring.bean.context;


/**
 * 빈 저장소 인터페이스
 */
public interface ApplicationContext {

    /**
     * 빈 객체 반환
     */
    Object getBean(String beanName);

    /**
     * 빈 저장소 초기화
     */
    void initialize(String basePackage) throws Exception;

    /**
     * 빈 이름 반환
     */
    String getBeanName(Class<?> clazz);
}
