package com.zw.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FirstController {
    @RequestMapping(value = "/test")
    public String hehe() {
        return "first";
    }

    @RequestMapping(value = "heihei")
    public String haha(){
        return "other";
    }

    @RequestMapping("/")
    public String toIndex(){
        return "index";
    }
}
