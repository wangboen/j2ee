package com.wbe.j2ee.service;

import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 用户登录，返回登录用户相关信息
     * @param user 拥有输入的username和password的User对象
     * @return 登录用户相关信息
     */
    User login(User user);

    /**
     * 用户注册，同时发送一封激活邮件
     * @param user 拥有输入的username，password和useremail的User对象，status=0，code为生成的UUID，即未激活
     */
    void register(User user);

    /**
     * 根据激活码code查询用户，之后再进行激活
     * @param code 生成的UUID作为激活码
     * @return 注册用户相关信息
     */
    User checkCode(String code);

    /**
     * 激活账户，修改用户状态
     * @param user
     */
    void updateUserStatus(User user);

    /**
     * 修改用户信息
     * @param user
     */
    void modify(User user);

    /**
     * 注销账户，修改用户状态为"2"
     * @param user
     */
    void delete(User user);

    void pay(Map<String,Object> map);
}
