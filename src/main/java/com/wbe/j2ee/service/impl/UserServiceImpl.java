package com.wbe.j2ee.service.impl;

import com.wbe.j2ee.dao.UserDao;
import com.wbe.j2ee.entity.User;
import com.wbe.j2ee.service.MailService;
import com.wbe.j2ee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final MailService mailService;

    @Autowired
    public UserServiceImpl(UserDao userDao, MailService mailService) {
        this.userDao = userDao;
        this.mailService = mailService;
    }

    @Override
    public User login(User user) {
        User user1 = userDao.login(user);
        if (user1 !=null){
            return user1;
        }
        return null;
    }

    @Override
    public void register(User user) {
        userDao.register(user);
        String code = user.getCode();
        System.out.println("code:"+code);
        String subject = "来自xxx网站的激活邮件";
        //user/checkCode?code=xxx即是我们点击邮件链接之后进行更改状态的
        String context = "<a href=\"http://localhost:8080/user/checkCode?code="+code+"\">激活请点击:"+code+"</a>";
        mailService.sendHtmlMail (user.getUseremail(),subject,context);
    }

    @Override
    public User checkCode(String code) {
        User user = userDao.checkCode(code);
        if (user != null){
            return user;
        }
        return null;
    }

    @Override
    public void updateUserStatus1(User user) {
        userDao.updateUserStatus1(user);
    }

    @Override
    public void updateUserStatus2(User user) {
        userDao.updateUserStatus2(user);
    }

    @Override
    public void modify(User user) {
        userDao.modify(user);
    }

    @Override
    public User selectByName(String username) {
        return userDao.selectByName(username);
    }

    @Override
    public User selectByEmail(String useremail) {
        return userDao.selectByEmail(useremail);
    }

    @Override
    public User selectById(int userid){
        return userDao.selectById(userid);
    }

    @Override
    public void pay(Map map) {
        userDao.pay(map);
    }

    @Override
    public void cancel(Map map) {
        userDao.cancel(map);
    }
}