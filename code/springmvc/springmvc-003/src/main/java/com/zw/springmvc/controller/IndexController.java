package com.zw.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping({"/testVal1", "/testVal2"})
    public String testRequestMappingValue(){
        return "test_value";
    }
}
