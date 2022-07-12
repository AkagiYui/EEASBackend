package com.kenko.ceea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CeeaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CeeaBackendApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        int i = 1 / 0;
        return "CEEA Backend Application. ";
    }
}
