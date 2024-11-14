package com.zw.ssm.service.impl;

import com.zw.ssm.bean.User;
import com.zw.ssm.dao.UserDao;
import com.zw.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getById(Integer id) {
        return userDao.selectById(id);
    }
}
