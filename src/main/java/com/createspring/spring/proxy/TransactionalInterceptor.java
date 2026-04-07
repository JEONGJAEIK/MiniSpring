package com.createspring.spring.proxy;

import com.createspring.spring.annotation.Transactional;
import com.createspring.spring.jdbc.DataSourceTransactionManager;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


import java.lang.reflect.Method;

/**
 * 트랜잭셔널 인터셉터
 */
public class TransactionalInterceptor implements MethodInterceptor {
    private final DataSourceTransactionManager dataSourceTransactionManager;
    private final Object object;

    public TransactionalInterceptor(Object object, DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
        this.object = object;
    }

    /**
     * 커밋과 롤백의 부가사항을 삽입한다. 트랜잭셔널이 있는 메서드만 부가로직 실행
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (method.isAnnotationPresent(Transactional.class)) {
            dataSourceTransactionManager.begin();
            try {
                Object result = methodProxy.invoke(object, objects);
                dataSourceTransactionManager.commit();
                return result;
            } catch (Exception e) {
                dataSourceTransactionManager.rollback();
                throw e;
            } finally {
                dataSourceTransactionManager.close();
            }
        }
        return methodProxy.invoke(object, objects);
    }
}
