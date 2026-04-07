package com.createspring.spring.bean.post;

import com.createspring.spring.annotation.EventListener;
import com.createspring.spring.annotation.TransactionEventListener;
import com.createspring.spring.event.ApplicationListenerMethodAdapter;
import com.createspring.spring.event.SimpleEventListenerFactory;
import com.createspring.spring.event.TransactionListenerMethodAdapter;
import com.createspring.spring.event.TransactionalEventListenerFactory;
import com.createspring.spring.transaction.TransactionPhase;

import java.lang.reflect.Method;

/**
 * 이벤트리스너 빈 후처리기
 */
public class EventListenerProcessor {

    /**
     * 이벤트리스너 어노테이션이 존재하면 리스너 메타데이터를 등록한다.
     */
    public void eventListenerMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventListener.class)) {
                String beanName = toBeanName(clazz);
                Class<?>[] triggerEvent = method.getParameterTypes();
                ApplicationListenerMethodAdapter adapter = new ApplicationListenerMethodAdapter(beanName, method);
                SimpleEventListenerFactory.setListener(triggerEvent[0], adapter);
            } else if (method.isAnnotationPresent(TransactionEventListener.class)) {
                eventTransactionalListenerMethod(clazz, method);
            }
        }
    }

    /**
     * 트랜잭셔널 이벤트리스너 어노테이션이 존재하면 리스너 메타데이터를 등록한다.
     */
    private void eventTransactionalListenerMethod(Class<?> clazz, Method method) {
        String beanName = toBeanName(clazz);
        Class<?>[] triggerEvent = method.getParameterTypes();
        TransactionEventListener annotation = method.getAnnotation(TransactionEventListener.class);
        TransactionPhase phase = annotation.phase();
        TransactionListenerMethodAdapter adapter = new TransactionListenerMethodAdapter(beanName, method, phase);
        TransactionalEventListenerFactory.setListener(triggerEvent[0], adapter);
    }

    private String toBeanName(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }
}
