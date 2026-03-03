package com.createspring.spring;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private static Set<Class<?>> beanDefinitions = new HashSet<>();
    private static Map<Class<?>, Object> beans = new HashMap<>();

    public static void initialize(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        BeanDefinition.createBeanDefinition(basePackage);
        beanDefinitions = BeanDefinition.getBeanDefinitions();

        for (Class<?> clazz : beanDefinitions) {
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
        System.out.println(clazz + "의존관계 주입 완료");
        beans.put(clazz, instance);
        return clazz.cast(instance);
    }

    public Object getBean(Class<?> clazz) {
        return beans.get(clazz);
    }
}