package com.zw.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("userController")
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/detail")
//    @RequestMapping("/user/detail")
    public String userDetail(){
        return "user_detail";
    }
}
