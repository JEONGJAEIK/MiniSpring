package com.createspring.spring.bean;

import com.createspring.spring.event.ApplicationEventPublisher;
import com.createspring.spring.event.ApplicationListenerMethodAdapter;
import com.createspring.spring.event.SimpleEventListenerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 애플리케이션 컨텍스트를 구현한다.
 * 빈 저장소를 집약하여 빈 반환등의 기본 로직들을 추상화 시킴
 */
public class AbstractApplicationContext implements ApplicationContext, ApplicationEventPublisher {
    private final BeanFactory registry;
    private final SimpleEventListenerFactory factory;

    public AbstractApplicationContext(BeanFactory registry, SimpleEventListenerFactory factory) {
        this.registry = registry;
        this.factory = factory;
    }

    @Override
    public Object getBean(String beanName) {
        return registry.singletonMap.get(beanName);
    }

    @Override
    public String getBeanName(Class<?> clazz) {
        return registry.typeToNameMap.get(clazz);
    }

    @Override
    public void publishEvent(Object o) {
        Class<?> clazz = o.getClass();
        List<ApplicationListenerMethodAdapter> adapterList = factory.getAdapter(clazz);
        for (ApplicationListenerMethodAdapter adapter : adapterList) {
            String beanName = adapter.getBeanName();
            Method method = adapter.getMethod();
            Object bean = getBean(beanName);
            try {
                method.invoke(bean, o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
