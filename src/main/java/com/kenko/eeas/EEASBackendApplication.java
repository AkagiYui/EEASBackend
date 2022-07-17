package com.kenko.eeas;

import com.kenko.eeas.common.Result;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EEASBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(EEASBackendApplication.class, args);
    }

    @GetMapping("/")
    @Hidden
    public Result hello() {
        return Result.success("Engineering Education Accreditation System Backend.");
    }
}
