package com.zw.springmvc.controller;

import com.zw.springmvc.pojo.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

@Controller
public class RequestBodyController {
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestBody String requestBodyStr){
        System.out.println(requestBodyStr);
        return "ok";
    }

    /*@RequestMapping(value = "/save2", method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(@RequestBody User user){
        System.out.println(user);
        return "ok";
    }*/

    @RequestMapping(value = "/save2", method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(RequestEntity<User> requestEntity){
        HttpMethod method = requestEntity.getMethod();
        URI url = requestEntity.getUrl();
        HttpHeaders headers = requestEntity.getHeaders();
        System.out.println(method);
        System.out.println(url);
        System.out.println(headers);
        User body = requestEntity.getBody();
        System.out.println(body);
        MediaType contentType = requestEntity.getHeaders().getContentType();
        System.out.println(contentType);
        return "saveUser ok";
    }
}
