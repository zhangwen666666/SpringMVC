package com.zw.oa.controller;

import org.springmvc.stereotype.Controller;
import org.springmvc.ui.ModelMap;
import org.springmvc.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {
    @RequestMapping("/detail")
    public String detail(ModelMap modelMap){
        modelMap.addAttribute("orderNumber", "123141244123");
        return "detail";
    }
}
