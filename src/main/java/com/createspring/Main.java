package com.createspring;

import com.createspring.board.controller.PostCreateController;
import com.createspring.board.controller.PostSearchController;
import com.createspring.board.repository.BoardRepository;
import com.createspring.board.service.BoardService;
import com.createspring.spring.BeanFactory;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws LifecycleException, IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BeanFactory.initialize("com.createspring");
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context context = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(context, "postSearchController", BeanFactory.getBean(PostSearchController.class));
        context.addServletMappingDecoded("/post/search", "postSearchController");

        Tomcat.addServlet(context, "postCreateController", BeanFactory.getBean(PostCreateController.class));
        context.addServletMappingDecoded("/post/create", "postCreateController");

        tomcat.start();
        tomcat.getServer().await();
    }
}
