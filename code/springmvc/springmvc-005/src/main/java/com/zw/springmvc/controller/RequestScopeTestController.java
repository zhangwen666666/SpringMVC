package com.zw.springmvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class RequestScopeTestController {

    @RequestMapping("/testServletAPI")
    public String testServletAPI(HttpServletRequest request){
        request.setAttribute("testRequestScope", "在SpringMVC中使用原生API完成Request域数据共享");
        return "ok";
    }

    @RequestMapping("/testModel")
    public String testModel(Model model){
        model.addAttribute("testRequestScope","在SpringMVC中使用Model完成Request域数据共享");
        return "ok";
    }

    @RequestMapping("/testMap")
    public String testMap(Map<String, Object> map){
        map.put("testRequestScope","在SpringMVC中使用Map完成Request域数据共享");
        return "ok";
    }

    @RequestMapping("/testModelMap")
    public String testModelMap(ModelMap modelMap){
        modelMap.addAttribute("testRequestScope","在SpringMVC中使用ModelMap完成Request域数据共享");
        return "ok";
    }

    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("testRequestScope","在SpringMVC中使用ModelAndView类完成Request域数据共享");
        modelAndView.setViewName("ok");
        return modelAndView;
    }
}
