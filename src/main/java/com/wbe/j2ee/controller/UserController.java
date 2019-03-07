package com.wbe.j2ee.controller;

import com.wbe.j2ee.common.UUIDUtils;
import com.wbe.j2ee.entity.Order;
import com.wbe.j2ee.entity.Product;
import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.entity.User;
import com.wbe.j2ee.service.OrderService;
import com.wbe.j2ee.service.ProductService;
import com.wbe.j2ee.service.ResService;
import com.wbe.j2ee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResService resService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    /**
     * 跳转到登录页面，不需要登录
     * @return 跳转到登录页面
     */
    @RequestMapping(value = "/login")
    public String login(){
        return "user/login";
    }

    /**
     * 退出登录逻辑，清空session，跳转到登录页面
     * @param session 清空session
     * @return 跳转到登录页面
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "user/login";
    }

    /**
     * 跳转到注册页面，不需要登录
     * @return 跳转到注册页面
     */
    @RequestMapping(value = "/register")
    public String register(){
        return "user/register";
    }

    /**
     * 跳转到个人信息页面，需要登录后才能访问
     * @param session 判断是否登录
     * @return 跳转到个人信息页面
     */
    @RequestMapping(value = "/info")
    public String info(HttpSession session){
        if (session.getAttribute("userid")!=null){
            return "user/info";
        }
        return "user/error";
    }

    /**
     * 跳转到个人信息修改页面，需要登录后才能访问
     * @param session 判断是否登录
     * @return 跳转到个人信息修改页面
     */
    @RequestMapping(value = "/modify")
    public String modify(HttpSession session){
        if (session.getAttribute("userid")!=null){
            return "user/modify";
        }
        return "user/error";
    }

    /**
     * 跳转到餐厅列表页面，需要登录后才能访问
     * @param session 判断是否登录
     * @return 跳转到餐厅列表页面
     */
    @RequestMapping(value = "/reslist")
    public String reslist(HttpSession session){
//        if (session.getAttribute("userid")!=null){
//            return "user/reslist";
//        }
//        return "user/error";
        return "user/reslist";
    }

    /**
     * 跳转到餐厅商品列表页面，需要登录后才能访问
     * @param session 判断是否登录
     * @return 跳转到餐厅商品列表页面
     */
    @RequestMapping(value = "/prolist")
    public String prolist(HttpSession session) {
//        if (session.getAttribute("userid")!=null && session.getAttribute("restaurantuuid")!=null){
//            return "user/prolist";
//        }
//        return "user/error";
        return "user/prolist";
    }

    /**
     * 跳转到订单信息页面，需要登录下单结算后才能访问
     * @param session 判断登录下单结算后才能访问
     * @return 跳转到订单信息页面
     */
    @RequestMapping(value = "/order")
    public String order(HttpSession session) {
//        if (session.getAttribute("userid")!=null && session.getAttribute("orderid")!=null){
//            return "user/prolist";
//        }
//        return "user/error";
        return "user/order";
    }

    /**
     * 跳转到商品列表页面之前，需要存储点击的餐厅相关信息
     * @param session 存储点击的餐厅相关信息
     * @return 存储session成功
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public String prolist(@RequestBody Restaurant restaurant,HttpSession session){
        Restaurant restaurant1 = resService.selectByName(restaurant);
        session.setAttribute("restaurantuuid",restaurant1.getUUID());
        return "存储session成功";
    }

    /**
     * 登录逻辑，判断是否存在账号
     * @param user 拥有输入的username和password的User对象
     * @param session 记录在session里
     * @return 若存在，记录在session里，返回"用户登录成功"；不存在返回"用户名或密码错误"
     */
    @PostMapping(value = "/loginUser")
    @ResponseBody
    public String login(@RequestBody User user, HttpSession session){
        User user1 = userService.login(user);
        if (user1!=null){
            session.setAttribute("userid",user1.getId());
            session.setAttribute("username",user1.getUsername());
            session.setAttribute("useremail",user1.getUseremail());
            session.setAttribute("phone",user1.getPhone());
            session.setAttribute("address1",user1.getAddress1());
            session.setAttribute("address2",user1.getAddress2());
            session.setAttribute("address3",user1.getAddress3());
            return "用户登录成功";
        }
        return "用户名或密码错误";
    }

    /**
     * 注册逻辑，生成UUID作为code，status设置为0，发送邮件到注册邮箱
     * @param user 拥有输入的username，password和useremail的User对象
     * @return 跳转到成功发送邮件页面
     */
    @PostMapping(value = "/registerUser")
    @ResponseBody
    public String register(@RequestBody User user){
        User user1 = userService.login(user);
        if (user1==null){
            user.setStatus(0);
            String code = UUIDUtils.getUUID()+ UUIDUtils.getUUID();
            user.setCode(code);
            userService.register(user);
            return "已发送邮件，请前往邮箱激活账户";
        }
        return "该邮箱已被使用";
    }

    /**
     * 校验邮箱中的code激活账户
     * 首先根据激活码code查询用户，之后再把状态修改为"1"
     */
    @RequestMapping(value = "/checkCode")
    public String checkCode(String code){
        User user = userService.checkCode(code);
        System.out.println(user);
        //如果用户不等于null，把用户状态修改status=1
        if (user !=null){
            user.setStatus(1);
            //把code验证码清空，已经不需要了
            user.setCode("");
            System.out.println(user);
            userService.updateUserStatus(user);
        }
        return "user/login";
    }

    /**
     * 修改个人信息逻辑
     */
    @RequestMapping(value = "/modifyUser")
    @ResponseBody
    public String modify(@RequestBody User user, HttpSession session){
        user.setId(Integer.parseInt(session.getAttribute("userid").toString()));
        User u = userService.login(user);
        if (u!=null){
            return "该用户名已被占用";
        }
        userService.modify(user);
        u = userService.login(user);
        session.setAttribute("userid",u.getId());
        session.setAttribute("username",u.getUsername());
        session.setAttribute("useremail",u.getUseremail());
        session.setAttribute("phone",u.getPhone());
        session.setAttribute("address1",u.getAddress1());
        session.setAttribute("address2",u.getAddress2());
        session.setAttribute("address3",u.getAddress3());
        return "修改成功";
    }

    /**
     * 注销逻辑，但不删除数据
     * @param user 要注销的用户相关信息
     * @return 返回登录页面
     */
    @RequestMapping(value = "/deleteUser")
    public String delete(User user, HttpSession session){
        user.setId(Integer.parseInt(session.getAttribute("userid").toString()));
        userService.delete(user);
        session.invalidate();
        return "user/login";
    }

    /**
     * 返回所有通过审批的餐厅列表
     * @return List<Restaurant>
     */
    @ResponseBody
    @RequestMapping(value = "/refreshres")
    public List<Restaurant> refreshres(HttpSession session){
        session.setAttribute("address1","江苏省南京市");
        String address = session.getAttribute("address1").toString().substring(0,6);
        return resService.search(address);
    }

    /**
     * 返回选中餐厅所有的商品列表
     * @return List<Restaurant>
     */
    @ResponseBody
    @RequestMapping(value = "/refreshpro")
    public List<Product> refreshpro(HttpSession session){
        String resuuid = session.getAttribute("restaurantuuid").toString();
        return productService.getProList(resuuid);
    }

    /**
     * 结算订单，订单状态为已下单1，用户的钱转到经理账上，相关商品库存减对应值
     * @return 下单成功
     */
    @ResponseBody
    @PostMapping(value = "/pay")
    public String pay(@RequestBody Order[] orderlist,HttpSession session){
        //int userid = Integer.parseInt(session.getAttribute("userid").toString());
        int userid = 1;
        float total = 0;
        String restaurantuuid = session.getAttribute("restaurantuuid").toString();
        List<Product> productList = new ArrayList<>();
        for (int i=0;i<orderlist.length;i++){
            orderlist[i].setUserid(userid);
            orderlist[i].setRestaurantuuid(restaurantuuid);
            total += Float.parseFloat(orderlist[i].getSubtotal().toString());
            Product product = new Product();
            product.setRestaurantUUID(restaurantuuid);
            product.setName(orderlist[i].getName());
            product.setNumber(orderlist[i].getNumber());
            product.setDate(new Date());
            productList.add(product);
        }
        productService.consume(productList);
        orderService.add(orderlist);
        session.setAttribute("orderid",orderService.getOrder());
        session.setAttribute("total",total);
        Map<String,Object> map = new HashMap<>();
        map.put("userid",userid);
        map.put("total",total);
        userService.pay(map);
        return "下单成功";
    }

    @ResponseBody
    @GetMapping(value = "/refreshorder")
    public List<Order> refreshorder(HttpSession session){
        int orderid = Integer.parseInt(session.getAttribute("orderid").toString());
        List<Order> orderList = orderService.selectById(orderid);
        return orderList;
    }

    @PostMapping(value = "/order_confirm")
    public String order_confirm(){
        return "确认收货";
    }

    @PostMapping(value = "/order_cancel")
    public String order_cancel(){
        return "取消订单";
    }
}