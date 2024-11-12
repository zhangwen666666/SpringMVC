package com.zw.springmvc.controller;

import com.zw.springmvc.pojo.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class AjaxController {
/*    @RequestMapping(value="/ajax", method = RequestMethod.GET)
    public String ajax(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.print("hello ajax!!!");
        return null;
    }*/

/*    @RequestMapping(value="/ajax", method = RequestMethod.GET)
    @ResponseBody
    public String ajax(){
        //return "hello ajax!!!";
        return "{\"id\":\"111112222\", \"username\":\"zhangsan\", \"password\":\"1234\"}";
    }*/

    @RequestMapping(value="/ajax", method = RequestMethod.GET)
    @ResponseBody
    public User ajax(){
        User user = new User(111L, "zhangsan","123");
        return user;
    }
}
