package com.fresh.dbfinjector;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DBFInjectorApplication {

    public static void main(String[] args) {
//        SpringApplication.run(AlohaInjectorApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(DBFInjectorApplication.class);
        builder.headless(false).run(args);
    }
}
