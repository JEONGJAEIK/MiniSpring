package com.createspring.spring;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private static Map<Class<?>, Object> beans = new HashMap<>();

    public static void initialize(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Set<Class<?>> beanDefinition = BeanDefinition.initBeanDefinition(basePackage);

        for (Class<?> clazz : beanDefinition) {
            dependencyInject(clazz);
        }
    }

    public static <T> T dependencyInject(Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (beans.containsKey(clazz)) {
            return clazz.cast(beans.get(clazz));
        }

        Constructor<?> constructor = clazz.getConstructors()[0];
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] dependencies = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            dependencies[i] = dependencyInject(paramTypes[i]);
        }

        Object instance = constructor.newInstance(dependencies);
        beans.put(clazz, instance);
        System.out.println(clazz + "의존관계 주입 완료");
        return clazz.cast(instance);
    }

    public static <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }
}