package com.craig.itemgame.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {

    @GetMapping("/test")
    public String Test() {
        return "Spring boot is running";
    }
}
