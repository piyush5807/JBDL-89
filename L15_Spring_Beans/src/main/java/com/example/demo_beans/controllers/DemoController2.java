package com.example.demo_beans.controllers;

import com.example.demo_beans.mappers.MyMapper;
import com.example.demo_beans.configs.PersonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController2 {

    @Autowired
    private PersonConfig personConfig;

    @Autowired
    MyMapper myMapper;

    @GetMapping("/test2")
    public String test2(){

        System.out.println(personConfig + " " + myMapper);
//        System.out.println("Inside test2, person max capacity " + personConfig.getMaxCount() + ", person min capacity " +  personConfig.getMinCount());
        return "Hello World2!!";
    }
}
