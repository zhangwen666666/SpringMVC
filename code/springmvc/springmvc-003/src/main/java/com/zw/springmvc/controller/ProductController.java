package com.zw.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("productController")
@RequestMapping("/product")
public class ProductController {
    @RequestMapping("/detail")
//    @RequestMapping("/product/detail")
    public String productDetail(){
        return "product_detail";
    }
}
