package com.createspring.spring;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class BeanDefinition {
    private static Set<Class<?>> beanDefinition = new HashSet<>();

    public static void createBeanDefinition(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException {
        Set<Class<?>> classes = ComponentScan.scanPackage(basePackage);

        for (Class<?> clazz : classes) {
            System.out.println(clazz.toString() + "빈 삽입 검사");
            if (clazz.isAnnotationPresent(Service.class) ||
                clazz.isAnnotationPresent(Repository.class) ||
                clazz.isAnnotationPresent(RestController.class)) {
                beanDefinition.add(clazz);
                System.out.println(clazz.toString() + "빈 정의 삽입");
            }
        }
    }

    public static Set<Class<?>> getBeanDefinitions() {
        return beanDefinition;
    }
}
