package com.zw.springmvc.controller;

import com.zw.springmvc.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getAll() {
        System.out.println("正在查询用户信息....");
        return "ok";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") Integer id) {
        System.out.println("正在查询id=" + id + "的用户信息...");
        return "ok";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String save(User user) {
        System.out.println("正在保存用户...");
        System.out.println(user);
        return "ok";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String modify(User user) {
        System.out.println(user);
        return "ok";
    }

    @RequestMapping(value = {"/user/{id}"}, method = RequestMethod.DELETE)
    public String deleteById(@PathVariable("id") Integer id) {
        System.out.println("正在删除id=" + id + "的用户...");
        return "ok";
    }
}
