package com.zw.usermgt.dao;

import com.zw.usermgt.bean.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("userDao")
public class UserDao {

    private static List<User> users = new ArrayList<>();

    static {
        User user1 = new User(1001L, "张三", 1, "zhangsan@qq.com");
        User user2 = new User(1002L, "李四", 0, "lisi@qq.com");
        User user3 = new User(1003L, "王五", 1, "wangwu@qq.com");
        User user4 = new User(1004L, "赵六", 0, "zhaoliu@qq.com");
        User user5 = new User(1005L, "钱七", 0, "qianqi@qq.com");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
    }

    /**
     * 查询所有用户信息
     *
     * @return 用户列表
     */
    public List<User> selectAll() {
        return users;
    }

    /**
     * 保存用户信息
     *
     * @param user
     */
    public void insert(User user) {
        Long id = System.currentTimeMillis();
        user.setId(id);
        users.add(user);
    }

    public User selectById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().get();
    }

    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (user.getId().equals(users.get(i).getId())) {
                users.set(i, user);
                break;
            }
        }
    }

    public void deleteById(Long id) {
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            users.remove(index);
        }
    }
}
