package com.sqltrans.hikari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HikariApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HikariApplication.class, args);
    }
}