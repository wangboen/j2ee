package com.wbe.j2ee.controller;

import com.wbe.j2ee.entity.Product;
import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.service.ProductService;
import com.wbe.j2ee.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/res")
public class ResController {

    @Autowired
    private ResService resService;

    @Autowired
    private ProductService productService;

    /**
     * 跳转到登录页面，不需要登录
     * @return 跳转到登录页面
     */
    @RequestMapping(value = "/login")
    public String login(){
        return "restaurant/login";
    }

    /**
     * 退出登录逻辑，清空session，跳转到登录页面
     * @param session 清空session
     * @return 跳转到登录页面
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "restaurant/login";
    }

    /**
     * 跳转到注册页面，不需要登录
     * @return 跳转到注册页面
     */
    @RequestMapping(value = "/register")
    public String register(){
        return "restaurant/register";
    }

    /**
     * 跳转到餐厅信息页面，需要登录后才能访问
     * @param session 根据session存的uuid值判断餐厅是否存在
     * @return 跳转到餐厅信息页面
     */
    @RequestMapping(value = "/info")
    public String info(HttpSession session){
        if (session.getAttribute("uuid")!=null){
            return "restaurant/info";
        }
        return "error";
    }

    /**
     * 跳转到修改餐厅信息页面，需要登录后才能访问
     * @param session 根据session存的uuid值查找餐厅相关信息
     * @return 跳转到修改餐厅信息页面
     */
    @RequestMapping(value = "/modify")
    public String modify(HttpSession session){
        if (session.getAttribute("uuid")!=null){
            return "restaurant/modify";
        }
        return "error";
    }

    /**
     * 根据session存的uuid值跳转到餐厅商品发布页面，需要登录后才能访问
     * @param session 获取session存的uuid值
     * @return 跳转到商品发布页面
     */
    @RequestMapping(value = "/release")
    public String release(HttpSession session){
        if (session.getAttribute("uuid")!=null){
            return "restaurant/release";
        }
        return "error";
    }

    /**
     * 登录逻辑，判断是否存在账号
     * @param restaurant 获取输入的UUID值，用session进行存储
     * @return 若存在，记录在session里，返回"用户登录成功"；不存在返回"用户名或密码错误"
     */
    @RequestMapping(value = "/loginRes")
    @ResponseBody
    public String login(@RequestBody Restaurant restaurant, HttpSession session){
        Restaurant restaurant1 = resService.login(restaurant);
        if (restaurant1 !=null){
            session.setAttribute("uuid",restaurant1.getUUID());
            session.setAttribute("name",restaurant1.getName());
            session.setAttribute("address",restaurant1.getAddress());
            session.setAttribute("type",restaurant1.getType());
            session.setAttribute("status",restaurant1.getStatus());
            return "餐厅登录成功";
        }
        return "不存在此餐厅UUID";
    }

    /**
     * 注册逻辑
     * @return
     */
    @RequestMapping(value = "/registerRes")
    public String register(HttpSession session){
        Restaurant restaurant = resService.register();
        session.setAttribute("uuid",restaurant.getUUID());
        session.setAttribute("name",restaurant.getName());
        session.setAttribute("address",restaurant.getAddress());
        session.setAttribute("type",restaurant.getType());
        session.setAttribute("status",restaurant.getStatus());
        return "restaurant/info";
    }

    /**
     * 修改餐厅逻辑
     * @return
     */
    @RequestMapping(value = "/modifyRes")
    @ResponseBody
    public String modify(@RequestBody Restaurant restaurant,HttpSession session){
        Restaurant restaurant1 = resService.selectByName(restaurant);
        if (restaurant1 !=null){
            return "餐厅名已被占用";
        }
        restaurant.setUUID(session.getAttribute("uuid").toString());
        resService.modify(restaurant);
        session.setAttribute("uuid",restaurant.getUUID());
        session.setAttribute("name",restaurant.getName());
        session.setAttribute("address",restaurant.getAddress());
        session.setAttribute("type",restaurant.getType());
        session.setAttribute("status",restaurant.getStatus());
        return "修改成功";
    }

    /**
     * 添加商品，如果当前status为0则不能添加
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public String Add(@RequestBody Product product,HttpSession session){
        product.setRestaurantUUID(session.getAttribute("uuid").toString());
        int status = Integer.parseInt(session.getAttribute("status").toString());
        if (status!=1){
            return "本餐厅修改信息尚未被批准";
        }
        Date date = new Date();
        product.setDate(date);
        productService.add(product);
        return "商品添加成功";
    }

    /**
     * 获取商品列表，根据session里的餐厅uuid来查询所有的该餐厅商品
     * @param session 获取session里的餐厅uuid
     * @return 该餐厅商品列表
     */
    @ResponseBody
    @GetMapping(value = "/getList")
    public List<Product> getList(HttpSession session){
        String uuid = session.getAttribute("uuid").toString();
        List<Product> productList = productService.getProList(uuid);
        return productList;
    }
}
