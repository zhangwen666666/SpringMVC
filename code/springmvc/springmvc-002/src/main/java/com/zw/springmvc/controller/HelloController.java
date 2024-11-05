package com.zw.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("helloController")
public class HelloController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
