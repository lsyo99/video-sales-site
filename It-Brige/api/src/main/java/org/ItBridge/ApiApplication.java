package org.ItBridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.ItBridge")
public class ApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(ApiApplication.class,args);
    }
}