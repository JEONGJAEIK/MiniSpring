package com.createspring.spring;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class ComponentScan {

    public static Set<Class<?>> scanComponent(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException {
        Set<Class<?>> classes = scanPackage(basePackage);
        Set<Class<?>> beanDefinition = new HashSet<>();

        for (Class<?> clazz : classes) {
            System.out.println(clazz.toString() + "빈 삽입 검사");
            if (clazz.isAnnotationPresent(Service.class) ||
                    clazz.isAnnotationPresent(Repository.class) ||
                    clazz.isAnnotationPresent(RestController.class)) {
                beanDefinition.add(clazz);
                System.out.println(clazz + "빈 정의 삽입");
            }
        }
        return beanDefinition;
    }

    private static Set<Class<?>> scanPackage(String packageName) throws IOException, URISyntaxException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        String path = packageName.replace(".", "/");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.toURI());
            System.out.println("클래스로더 검사중 " + resource + ", " + directory);

            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    classes.addAll(scanPackage(packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    classes.add(Class.forName(className));
                }
            }
        }
        return classes;
    }

}
