package com.example.peterassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@SpringBootApplication
public class PeterAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeterAssignmentApplication.class, args);
        System.out.println("Done!");

    }

}
