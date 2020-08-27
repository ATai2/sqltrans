package com.ppx.sbsql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//        ImportSqlFileService importSqlFileService = context.getBean("importSqlFileService", ImportSqlFileService.class);
//        importSqlFileService.excuteFile(args);
    }
}