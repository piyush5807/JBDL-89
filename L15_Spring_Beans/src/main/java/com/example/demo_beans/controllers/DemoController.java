package com.example.demo_beans.controllers;

import com.example.demo_beans.configs.PersonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // singleton
public class DemoController {

    @Autowired
    PersonConfig personConfig;

    @GetMapping("/test")
    public String test() {

        System.out.println(personConfig);
        System.out.println("person max capacity " + personConfig.getMaxCount() + ", person min capacity " +  personConfig.getMinCount());
        personConfig.setMaxCount(20);

        return "Hello World!!";
    }
}
