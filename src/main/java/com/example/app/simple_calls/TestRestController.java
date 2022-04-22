package com.example.app.simple_calls;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @RequestMapping("/")
    public String sample() {
        return "Maven Spring Boot";
    }
}
