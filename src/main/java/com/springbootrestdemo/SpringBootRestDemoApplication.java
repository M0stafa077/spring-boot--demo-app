package com.springbootrestdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootRestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestDemoApplication.class, args);
    }

}
