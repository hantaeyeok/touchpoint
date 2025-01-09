package com.touchpoint.kh.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReactTest {

    @GetMapping("/test")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("Hello from Spring Boot!");
    }
}