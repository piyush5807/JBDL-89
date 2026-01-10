package com.example.demo_beans.configs;

import com.example.demo_beans.mappers.MyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Configuration class is injected as a dependency
 * Configuration - Singleton    Bean Object Mapper  - Singleton     ==> single object created on application startup and will be reused in all the api calls
 * Configuration - Singleton    Bean Object Mapper  - Prototype     ==> no object created on application startup and on subsequent API calls a new object is created
 * Configuration - Prototype    Bean Object Mapper  - Singleton     ==> single object created on application startup and will be reused in all the api calls
 * Configuration - Prototype    Bean Object Mapper  - Prototype     ==> no object created on application startup and on subsequent API calls a new object is created
 *
 *
 * Bean mapper class is injected as a dependency
 * Configuration - Singleton    Bean Object Mapper  - Singleton     ==> single object created on application startup and will be reused in all the api calls
 * Configuration - Singleton    Bean Object Mapper  - Prototype     ==> single object created on application startup and will be reused in all the api calls
 * Configuration - Prototype    Bean Object Mapper  - Singleton     ==> single object created on application startup and will be reused in all the api calls
 * Configuration - Prototype    Bean Object Mapper  - Prototype     ==> single object created on application startup and will be reused in all the api calls
 *
 *
 */

@Configuration
public class DemoConfig {

    @Bean // default scope is singleton
    /**
     * By the virtue of this @Bean annotation (Inversion of control), spring boot will invoke
     * this function during the application startup
     */
    @Scope("prototype")
    public MyMapper getObjectMapper(){
        MyMapper mapper = new MyMapper();
        System.out.println("Creating new my object mapper, ref - " + mapper);
        return mapper;
    }

}
