package com.project.loggingmiddleware.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public String getHello() {
        return "Hello, World!";
    }

    @PostMapping("/echo")
    public String postEcho(@RequestBody String message) {
        return "Echo: " + message;
    }

    @PutMapping("/update")
    public String putUpdate(@RequestBody String updateMessage) {
        return "Updated message: " + updateMessage;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        return "Deleted item with ID: " + id;
    }
}
