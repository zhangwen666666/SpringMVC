package com.zw.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping(value = "index")
    public String toIndex(){
        System.out.println("IndexController处理器方法执行了");
        return "index";
    }

    @RequestMapping("ok")
    public String toOk(){
        System.out.println("IndexController处理器的toOK()方法执行了");
        return "ok";
    }
}
