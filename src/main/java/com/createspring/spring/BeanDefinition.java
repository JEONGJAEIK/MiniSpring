package com.createspring.spring;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class BeanDefinition {
    private static Set<Class<?>> beanDefinition = new HashSet<>();

    public static Set<Class<?>> initBeanDefinition(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException {
        beanDefinition = ComponentScan.scanComponent(basePackage);
        return beanDefinition;
    }
}
