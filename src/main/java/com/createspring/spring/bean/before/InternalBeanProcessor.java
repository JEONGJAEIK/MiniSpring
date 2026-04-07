package com.createspring.spring.bean.before;

import com.createspring.spring.bean.AbstractApplicationContext;
import com.createspring.spring.bean.BeanDefinition;
import com.createspring.spring.bean.BeanFactory;
import com.createspring.spring.bean.DefaultSingletonBeanRegistry;
import com.createspring.spring.event.ApplicationEventPublisher;
import com.createspring.spring.event.SimpleEventListenerFactory;
import com.createspring.spring.jdbc.DataSourceTransactionManager;

/**
 * 내부 빈 등록기
 * 사용자 정의 빈을 등록하기 전에 필수 빈들을 등록한다.
 */
public class InternalBeanProcessor {

    /**
     * 필수 빈들을 미리 등록한다.
     */
    public static void process(DefaultSingletonBeanRegistry registry) {
        createContext(registry);
        createTxManager(registry);
    }

    /**
     * 애플리케이션 컨텍스트를 미리 빈으로 등록한다.
     */
    public static void createContext(DefaultSingletonBeanRegistry registry) {
        AbstractApplicationContext applicationContext = new AbstractApplicationContext((BeanFactory) registry, new SimpleEventListenerFactory());
        registry.setBeanMap(applicationContext, new BeanDefinition(AbstractApplicationContext.class));
        registry.registerTypeMapping(ApplicationEventPublisher.class, AbstractApplicationContext.class);
    }

    /**
     * 트랜잭션 매니저를 미리 빈으로 등록한다.
     * //TODO 데이터소스를 등록하는 것으로 변경 필요
     */
    public static void createTxManager(DefaultSingletonBeanRegistry registry) {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        registry.setBeanMap(txManager, new BeanDefinition(DataSourceTransactionManager.class));
    }
}
