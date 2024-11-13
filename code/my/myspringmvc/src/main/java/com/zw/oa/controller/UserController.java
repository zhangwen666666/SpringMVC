package com.zw.oa.controller;

import org.springmvc.stereotype.Controller;
import org.springmvc.ui.ModelMap;
import org.springmvc.web.bind.annotation.RequestMapping;
import org.springmvc.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String toIndex(ModelMap modelMap){
        modelMap.addAttribute("username","李四");
        return "index";
    }
}
