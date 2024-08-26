package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class ProtectSecurityController {
    @GetMapping
    public String protectedResource() {
        return "This is a protected resource";
    }
}
