package com.zw.springmvc.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"testSessionScope","x"})
public class SessionScopeTestController {
    @RequestMapping("testSessionServletAPI")
    public String testServletAPI(HttpSession session){
        session.setAttribute("testSessionScope","在SpringMVC中使用ServletAPI完成Session域数据共享");
        return "ok";
    }

    @RequestMapping("testSessionAttributes")
    public String testSessionAttributes(Model model){
        model.addAttribute("testSessionScope","在SpringMVC中使用SessionAttributes注解完成Session域数据共享");
        model.addAttribute("x","我是X");
        return "ok";
    }
}
