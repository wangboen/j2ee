package com.wbe.j2ee.service.impl;

import com.wbe.j2ee.dao.UserDao;
import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.entity.User;
import com.wbe.j2ee.service.MailService;
import com.wbe.j2ee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    /**
     * 用户登录，返回登录用户相关信息
     * @param user 拥有输入的username和password的User对象
     * @return 登录用户相关信息
     */
    @Override
    public User login(User user) {
        User user1 = userDao.login(user);
        if (user1 !=null){
            return user1;
        }
        return null;
    }

    /**
     * 用户注册，同时发送一封激活邮件
     * @param user 拥有输入的username，password和useremail的User对象，status=0，code为生成的UUID，即未激活
     */
    @Override
    public void register(User user) {
        userDao.register(user);
        String code = user.getCode();
        System.out.println("code:"+code);
        String subject = "来自xxx网站的激活邮件";
        //user/checkCode?code=xxx即是我们点击邮件链接之后进行更改状态的
        String context = "<a href=\"http://localhost:8080/user/checkCode?code="+code+"\">激活请点击:"+code+"</a>";
        //发送激活邮件
        mailService.sendHtmlMail (user.getUseremail(),subject,context);
    }

    /**
     * 根据激活码code查询用户，之后再进行激活
     * @param code 生成的UUID作为激活码
     * @return 注册用户相关信息
     */
    @Override
    public User checkCode(String code) {
        return userDao.checkCode(code);
    }

    /**
     * 激活账户，修改用户状态
     * @param user
     */
    @Override
    public void updateUserStatus(User user) {
        userDao.updateUserStatus(user);
    }

    /**
     * 修改用户信息
     * @param user
     */
    @Override
    public void modify(User user) { userDao.modify(user); }

    /**
     * 注销账户，修改用户状态为"2"
     * @param user
     */
    @Override
    public void delete(User user) { userDao.delete(user); }

    @Override
    public void pay(Map<String, Object> map) {
        userDao.pay1(map);
        userDao.pay2(Float.parseFloat(map.get("total").toString()));
    }
}