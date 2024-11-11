package com.zw.usermgt.controller;

import com.zw.usermgt.bean.User;
import com.zw.usermgt.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = {"/user"}, method = RequestMethod.GET)
    public String list(Model model){
        // 查询数据库，获取用户列表List集合
        List<User> users = userDao.selectAll();
        // 将用户列表存储到request域中
        model.addAttribute("users", users);
        // 转发到视图
        return "user_list";
    }

    @PostMapping("/user")
    public String save(User user){
        userDao.insert(user);
        // 重定向到用户列表页面
        return "redirect:/user";
    }

    @GetMapping("/user/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        User user = userDao.selectById(id);
        model.addAttribute(user);
        return "user_edit";
    }

    @PutMapping("/user")
    public String modify(User user){
        userDao.update(user);
        return "redirect:/user";
    }

    @DeleteMapping("/user/{id}")
    public String deleteById(@PathVariable("id") Long id){
        userDao.deleteById(id);
        return "redirect:/user";
    }
}
