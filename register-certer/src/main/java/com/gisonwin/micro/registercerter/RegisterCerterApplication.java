package com.gisonwin.micro.registercerter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegisterCerterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterCerterApplication.class, args);
    }

}
