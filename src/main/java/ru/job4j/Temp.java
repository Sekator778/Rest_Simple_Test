package ru.job4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Temp {
    public static void main(String[] args) {
        SpringApplication.run(Temp.class, args);
    }

    @RequestMapping("/")
    public String greetings() {
        return "<h1>Spring Boot Rocks in Java too!</h1>";
    }
}