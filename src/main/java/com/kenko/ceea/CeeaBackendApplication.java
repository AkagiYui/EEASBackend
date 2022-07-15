package com.kenko.ceea;

import com.kenko.ceea.common.Result;
import io.swagger.v3.oas.annotations.Hidden;
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
    @Hidden
    public Result hello() {
        return Result.success("CEEA Backend Application. ");
    }
}
