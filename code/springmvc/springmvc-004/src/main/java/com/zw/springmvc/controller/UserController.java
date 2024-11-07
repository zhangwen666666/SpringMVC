package com.zw.springmvc.controller;

import com.zw.springmvc.bean.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
public class UserController {
    @RequestMapping("/")
    public String toRegister(){
        return "register";
    }

/*    @PostMapping("/user/reg")
    public String register(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        System.out.println(request);
        System.out.println(response);
        System.out.println(session);

        return "ok";
    }*/

/*    @PostMapping("/user/reg")
    public String register(
            @RequestParam("username")
            String username,
            @RequestParam("password")
            String password,
            @RequestParam(value = "sex", required = false)
            Integer sex,
            @RequestParam("hobby")
            String[] hobby,
            @RequestParam("introduction")
            String introduction
    ){
        System.out.println(username);
        System.out.println(password);
        System.out.println(sex);
        System.out.println(Arrays.toString(hobby));
        System.out.println(introduction);
        return "ok";
    }*/

    /*@PostMapping("/user/reg")
    public String register(
            String username,
            String password,
            Integer sex,
            String[] hobby,
            String introduction
    ){
        System.out.println(username);
        System.out.println(password);
        System.out.println(sex);
        System.out.println(Arrays.toString(hobby));
        System.out.println(introduction);
        return "ok";
    }*/

    @PostMapping("/user/reg")
    public String register(User user){
        System.out.println(user);
        return "ok";
    }
}
