package com.fresh.alohainjector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AlohaInjectorApplication {

    public static void main(String[] args) {
//        SpringApplication.run(AlohaInjectorApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AlohaInjectorApplication.class);
        builder.headless(false).run(args);
    }
}
