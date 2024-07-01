package com.amazontest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public ResponseEntity<?> getMapping(HttpServletRequest request) {
        return ResponseEntity.ok().body("GET REQUEST " + request.getRemoteAddr() + " " + request.getRequestURI() + " " + LocalDateTime.now());
    }

    @PostMapping
    public ResponseEntity<?> postMapping(HttpServletRequest request) {
        return ResponseEntity.ok().body("POST REQUEST " +request.getRemoteAddr()  + " " + request.getRequestURI() + " " + LocalDateTime.now());
    }
}
