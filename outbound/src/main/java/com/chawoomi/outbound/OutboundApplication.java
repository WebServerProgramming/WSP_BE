package com.chawoomi.outbound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.chawoomi")
@ComponentScan(basePackages = "com.chawoomi")
public class OutboundApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutboundApplication.class, args);
    }

}