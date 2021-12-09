package com.example.demo3.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("demo3 相关API")
@RestController
@RequestMapping("/demo3/zipkin")
public class ZipkinController {
    @GetMapping("/test")
    public String test() {
        return "demo3 test response";
    }
}

