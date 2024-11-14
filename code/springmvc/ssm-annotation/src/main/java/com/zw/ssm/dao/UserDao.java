package com.zw.ssm.dao;

import com.zw.ssm.bean.User;
import org.apache.ibatis.annotations.Select;

public interface UserDao {
    @Select("select * from t_user where id = #{id}")
    User selectById(Integer id);
}
