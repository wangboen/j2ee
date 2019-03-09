package com.wbe.j2ee.service;

import com.wbe.j2ee.entity.User;
import java.util.Map;

public interface UserService {
    User login(User user);

    void register(User user);

    User checkCode(String code);

    void updateUserStatus1(User user);

    void updateUserStatus2(User user);

    void modify(User user);

    User selectByName(String username);

    User selectByEmail(String useremail);

    User selectById(int userid);

    void pay(Map map);

    void cancel(Map map);
}
