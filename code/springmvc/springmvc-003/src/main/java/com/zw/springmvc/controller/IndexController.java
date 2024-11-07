package com.zw.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping({"/testVal1", "/testVal2"})
    public String testRequestMappingValue() {
        return "test_value";
    }


    @RequestMapping("/login/{username}/{password}")
    public String testRESTFulURL(
            @PathVariable("username") String username,
            @PathVariable("password") String password
    ) {
        System.out.println("用户名：" + username + "，密码：" + password);
        return "ok";
    }

    @RequestMapping(value = "/user/login",method = {RequestMethod.POST, RequestMethod.GET})
    public String userLogin(){
        System.out.println("处理登录的业务");
        return "ok";
    }

    @RequestMapping(value="/testParams", params = {"username!=zhangsan","password"})
    public String testParams(){
        return "ok";
    }
}
