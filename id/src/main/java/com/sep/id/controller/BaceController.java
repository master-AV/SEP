package com.sep.id.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("id")
public class BaceController {
    @GetMapping
    public String hello(){
        System.out.println("HELLO func");
        return "Hello";
    }
}
