package com.wbe.j2ee.dao;

import com.wbe.j2ee.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserDao {
    /**
     * 用户登录，根据username,password,status进行查询，返回查找到的账户
     */
    User login(User user);

    /**
     * 用户注册，status设置为0，存储生成的code验证码
     */
    void register(User user);

    /**
     * 根据code激活码查询账户，返回账户
     */
    User checkCode(String code);

    /**
     * 激活账户，status设置为1，清除验证码code
     */
    void updateUserStatus1(User user);

    /**
     * 注销账户，status设置为2
     */
    void updateUserStatus2(User user);

    /**
     * 修改个人信息
     */
    void modify(User user);

    /**
     * 避免出现相同名字
     */
    User selectByName(String username);

    /**
     * 避免出现已被使用过的邮箱
     */
    User selectByEmail(String useremail);

    /**
     * 根据userid查询user
     */
    User selectById(int userid);

    /**
     * 对应userid的用户预付款达到manager账上
     */
    void pay(Map map);

    /**
     * 取消订单，预付款退回账上
     */
    void cancel(Map map);

    /**
     * 刷新用户的VIP等级
     */
    void updateVIP(Map<String,Object> map);

    /**
     * 统计总共有多少活跃用户
     */
    int count();
}
