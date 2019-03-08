package com.wbe.j2ee.controller;

import com.wbe.j2ee.entity.Product;
import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.service.ProductService;
import com.wbe.j2ee.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login")
    public String login(){
        return "restaurant/login";
    }

    /**
     * 退出登录，跳转到登录页面，清空session
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "restaurant/login";
    }

    /**
     * 跳转到注册页面
     */
    @RequestMapping(value = "/register")
    public String register(){
        return "restaurant/register";
    }

    /**
     * 根据session中存的restaurantUUID属性跳转到餐厅信息页面，需要先登录，即验证session中是否有restaurantUUID属性
     */
    @RequestMapping(value = "/info")
    public String info(HttpSession session){
        if (session.getAttribute("restaurantUUID")!=null){
            return "restaurant/info";
        }
        return "restaurant/error";
    }

    /**
     * 跳转到修改餐厅信息页面，需要先登录，即验证session中是否有restaurantUUID属性
     */
    @RequestMapping(value = "/modify")
    public String modify(HttpSession session){
        if (session.getAttribute("restaurantUUID")!=null){
            return "restaurant/modify";
        }
        return "restaurant/error";
    }

    /**
     *根据session中存的restaurantUUID属性跳转到餐厅商品发布页面，需要先登录，即验证session中是否有restaurantUUID属性
     */
    @RequestMapping(value = "/release")
    public String release(HttpSession session){
        if (session.getAttribute("restaurantUUID")!=null){
            return "restaurant/release";
        }
        return "restaurant/error";
    }

    /**
     * 登录逻辑，判断是否存在账号
     */
    @PostMapping(value = "/loginRes")
    @ResponseBody
    public String login(@RequestBody Restaurant restaurant, HttpSession session){
        Restaurant restaurant1 = resService.login(restaurant.getRestaurantUUID());
        if (restaurant1 !=null){
            session.setAttribute("restaurantid",restaurant1.getRestaurantid());
            session.setAttribute("restaurantUUID",restaurant1.getRestaurantUUID());
            session.setAttribute("restaurantname",restaurant1.getRestaurantname());
            session.setAttribute("address",restaurant1.getAddress());
            session.setAttribute("type",restaurant1.getType());
            session.setAttribute("status",restaurant1.getStatus());
            return "餐厅登录成功";
        }
        return "不存在此餐厅UUID";
    }

    /**
     * 注册逻辑
     */
    @RequestMapping(value = "/registerRes")
    public String register(HttpSession session){
        Restaurant restaurant = resService.register();
        session.setAttribute("restaurantid",restaurant.getRestaurantid());
        session.setAttribute("restaurantUUID",restaurant.getRestaurantUUID());
        session.setAttribute("restaurantname",restaurant.getRestaurantname());
        session.setAttribute("address",restaurant.getAddress());
        session.setAttribute("type",restaurant.getType());
        session.setAttribute("status",restaurant.getStatus());
        return "restaurant/info";
    }

    /**
     * 修改餐厅信息逻辑
     */
    @PostMapping(value = "/modifyRes")
    @ResponseBody
    public String modify(@RequestBody Restaurant restaurant,HttpSession session){
        Restaurant restaurant1 = resService.selectByName(restaurant.getRestaurantname());
        if (restaurant1 != null){
            return "餐厅名已被占用";
        }
        restaurant.setRestaurantid(Integer.parseInt(session.getAttribute("restaurantid").toString()));
        resService.modify(restaurant);
        restaurant = resService.login(session.getAttribute("restaurantUUID").toString());
        session.setAttribute("restaurantname",restaurant.getRestaurantname());
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
        product.setRestaurantid(Integer.parseInt(session.getAttribute("restaurantid").toString()));
        int status = Integer.parseInt(session.getAttribute("status").toString());
        if (status != 1){
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
        int restaurantid = Integer.parseInt(session.getAttribute("restaurantid").toString());
        List<Product> productList = productService.getProList(restaurantid);
        return productList;
    }
}
