package com.rationalfx.clearbankgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ClearbankGatewayApplication implements CommandLineRunner {
    @Autowired
    private Environment env;

    public static void main(String[] args) {

        SpringApplication.run(ClearbankGatewayApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {

        System.out.println("------------------ CLEARBANK-GATEWAY APPLICATION STARTED FOR MODE : " + env.getProperty("targetenv")+" ------------------ ");

    }

}
