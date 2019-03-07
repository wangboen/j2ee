package com.wbe.j2ee.dao;

import com.wbe.j2ee.entity.User;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.util.Map;

@Repository
public interface UserDao {
    /**
     * 登录
     * @param user 登陆的用户名密码
     * @return 该用户的相关信息
     */
    User login(User user);

    /**
     * 用户注册
     * @param user 要注册的邮箱账号
     */
    void register(User user);

    /**
     * 根据激活码查询用户，之后再进行修改用户状态
     * @param code 生成的uuid邮箱验证码
     * @return 判断用户是否提交开户申请
     */
    User checkCode(String code);

    /**
     * 激活账户，修改用户状态为"1"
     * @param user 要激活的用户的uuid
     */
    void updateUserStatus(User user);

    /**
     * 修改用户信息
     * @param user 修改后的用户相关信息
     */
    void modify(User user);

    /**
     * 注销账户，修改用户状态为"2"
     * @param user 要修改的用户id
     */
    void delete(User user);

    /**
     * 预付给经理
     * @param map
     */
    void pay1(Map<String,Object> map);

    /**
     * 经理得到预付款
     * @param total 预付款总额
     */
    void pay2(float total);

    void confirm(float total);
}
