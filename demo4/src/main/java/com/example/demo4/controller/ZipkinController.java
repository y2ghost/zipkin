package com.example.demo4.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("demo4 相关API")
@RestController
@RequestMapping("/demo4/zipkin")
public class ZipkinController {
    @GetMapping("/test")
    public String test() {
        return "demo4 test response";
    }
}

