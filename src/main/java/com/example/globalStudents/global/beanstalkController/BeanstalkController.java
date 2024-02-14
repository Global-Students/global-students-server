package com.example.globalStudents.global.beanstalkController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeanstalkController {
    @GetMapping("/health")
    public String healthCheck(){
        return "HEALTHY";
    }
}
