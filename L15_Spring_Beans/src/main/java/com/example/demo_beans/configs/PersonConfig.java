package com.example.demo_beans.configs;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PersonConfig {

    private Integer maxCount;
    private Integer minCount;

    public PersonConfig() {
        System.out.println("PersonConfig - " + this);
        this.maxCount = 10;
        this.minCount = 1;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getMinCount() {
        return minCount;
    }

    public void setMinCount(Integer minCount) {
        this.minCount = minCount;
    }
}
