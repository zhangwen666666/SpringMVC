package com.zw.springmvc.service;

import com.zw.springmvc.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getById(Long id){
        if(id == 1){
            return new User(111L, "admin", "fdskal");
        }
        return null;
    }
}
