package com.example.demo_beans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
        basePackages = {"com.example.demo_beans", "another_package"}
//        excludeFilters = @ComponentScan.Filter(classes =  org.springframework.stereotype.Controller.class)

)
public class DemoBeansApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoBeansApplication.class, args);
	}

}
