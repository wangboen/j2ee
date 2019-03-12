package com.wbe.j2ee.controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.wbe.j2ee.common.UUIDUtils;
import com.wbe.j2ee.entity.Order;
import com.wbe.j2ee.entity.Product;
import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.entity.User;
import com.wbe.j2ee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final ResService resService;

    private final ProductService productService;

    private final OrderService orderService;

    private final ManagerService managerService;

    @Autowired
    public UserController(UserService userService, ResService resService, ProductService productService, OrderService orderService, ManagerService managerService) {
        this.userService = userService;
        this.resService = resService;
        this.productService = productService;
        this.orderService = orderService;
        this.managerService = managerService;
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login")
    public String login(){
        return "user/login";
    }

    /**
     * 退出登录，跳转到登录页面，清空session
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "user/login";
    }

    /**
     * 跳转到注册页面
     */
    @RequestMapping(value = "/register")
    public String register(){
        return "user/register";
    }

    /**
     * 根据session中存的userid属性跳转到个人详情页面，需要先登录，即验证session中是否有userid属性
     */
    @RequestMapping(value = "/info")
    public String info(HttpSession session){
        if (session.getAttribute("userid")!=null){
            return "user/info";
        }
        return "user/error";
    }

    /**
     * 跳转到修改个人信息页面，需要先登录，即验证session中是否有userid属性
     */
    @RequestMapping(value = "/modify")
    public String modify(HttpSession session){
        if (session.getAttribute("userid")!=null){
            return "user/modify";
        }
        return "user/error";
    }

    /**
     * 跳转到餐厅列表页面，需要先登录，即验证session中是否有userid属性
     */
    @RequestMapping(value = "/reslist")
    public String reslist(HttpSession session){
        if (session.getAttribute("userid")!=null){
            return "user/reslist";
        }
        return "user/error";
    }

    /**
     * 跳转到餐厅商品列表页面，需要先登录后点击餐厅进入，即验证session中是否有userid和restaurantuuid属性
     */
    @RequestMapping(value = "/prolist")
    public String prolist(HttpSession session) {
        if (session.getAttribute("userid")!=null && session.getAttribute("restaurantid")!=null){
            return "user/prolist";
        }
        return "user/error";
    }

    /**
     * 跳转到订单配送状态页面，需要先登录，点击餐厅，下单结算，即验证session中是否有userid,restaurantuuid和orderid属性
     */
    @RequestMapping(value = "/order")
    public String order(HttpSession session) {
        if (session.getAttribute("userid")!=null && session.getAttribute("restaurantid")!=null && session.getAttribute("orderid")!=null){
            return "user/order";
        }
        return "user/error";
    }

    /**
     * 跳转到历史订单页面，需要先登录，即验证session中是否有userid属性
     */
    @RequestMapping(value = "/orderlist")
    public String orderlist(HttpSession session){
        if (session.getAttribute("userid")!=null){
            return "user/orderlist";
        }
        return "user/error";
    }

    /**
     * 登录逻辑，username和password对应正确，且status=1,code为空
     * @param user 输入的username和password
     * @return 若存在，记录user的相关信息在session里，返回"用户登录成功"；不存在返回"用户名或密码错误"
     */
    @PostMapping(value = "/loginUser")
    @ResponseBody
    public String login(@RequestBody User user, HttpSession session){
        User user1 = userService.login(user);
        if (user1!=null){
            session.setAttribute("userid",user1.getUserid());
            session.setAttribute("useremail",user1.getUseremail());
            session.setAttribute("username",user1.getUsername());
            session.setAttribute("phone",user1.getPhone());
            session.setAttribute("account",user1.getAccount());
            session.setAttribute("vip",user1.getVip());
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
        User user1 = userService.selectByName(user.getUsername());
        User user2 = userService.selectByEmail(user.getUseremail());
        if (user2 != null){
            return "该邮箱已被占用";
        }
        if (user1 != null){
            return "该用户名已被使用";
        }
        user.setStatus(0);
        String code = UUIDUtils.getUUID()+ UUIDUtils.getUUID();
        user.setCode(code);
        userService.register(user);
        return "已发送邮件，请前往邮箱激活账户";
    }

    /**
     * 校验邮箱中的code激活账户
     * 根据code激活码查询账户，激活账户，status设置为1，清除验证码code
     */
    @RequestMapping(value = "/checkCode")
    public String checkCode(String code){
        User user = userService.checkCode(code);
        if (user != null){
            userService.updateUserStatus1(user);
        }
        return "user/login";
    }

    /**
     * 修改个人信息逻辑
     */
    @RequestMapping(value = "/modifyUser")
    @ResponseBody
    public String modify(@RequestBody User user, HttpSession session){
        int userid = Integer.parseInt(session.getAttribute("userid").toString());
        user.setUserid(userid);
        User user1 = userService.selectByName(user.getUsername());
        if (user1 != null){
            return "该用户名已被占用";
        }
        userService.modify(user);
        user1 = userService.selectById(userid);
        session.setAttribute("username",user1.getUsername());
        session.setAttribute("phone",user1.getPhone());
        session.setAttribute("address1",user1.getAddress1());
        session.setAttribute("address2",user1.getAddress2());
        session.setAttribute("address3",user1.getAddress3());
        return "修改成功";
    }

    /**
     * 注销逻辑，但不删除数据
     * @param user 要注销的用户相关信息
     * @return 返回登录页面
     */
    @RequestMapping(value = "/deleteUser")
    public String delete(User user, HttpSession session){
        user.setUserid(Integer.parseInt(session.getAttribute("userid").toString()));
        userService.updateUserStatus2(user);
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
        String address = session.getAttribute("address1").toString().substring(0,6);
        return resService.searchByAdress(address);
    }

    /**
     * 跳转到商品列表页面之前，需要存储点击的餐厅相关信息
     * @param session 存储点击的餐厅相关信息
     * @return 存储session成功
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public String prolist(@RequestBody Restaurant restaurant,HttpSession session){
        Restaurant restaurant1 = resService.selectByName(restaurant.getRestaurantname());
        session.setAttribute("restaurantid",restaurant1.getRestaurantid());
        return "存储session成功";
    }

    /**
     * 返回选中餐厅所有的商品列表
     * @return List<Restaurant>
     */
    @ResponseBody
    @RequestMapping(value = "/refreshpro")
    public List<Product> refreshpro(HttpSession session){
        int resid = Integer.parseInt(session.getAttribute("restaurantid").toString());
        return productService.getProList(resid);
    }

    /**
     * 结算订单，插入新的订单信息，status=0，用户的钱转到经理账上，相关商品库存减对应值
     * @return 下单成功
     */
    @ResponseBody
    @PostMapping(value = "/pay")
    public String pay(@RequestBody List<Map> maps,HttpSession session){
        List<Order> orders = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        int userid = Integer.parseInt(session.getAttribute("userid").toString());
        int restaurantid = Integer.parseInt(session.getAttribute("restaurantid").toString());

        /*
            插入新的订单信息，status=0
         */
        for (int i=0;i<maps.size();i++){
            String productname = maps.get(i).get("productname").toString();
            Product product = new Product();
            product.setProductname(productname);
            product.setRestaurantid(restaurantid);
            product = productService.selectByName(product);
            int productid = product.getProductid();
            Order order = new Order();
            order.setRestaurantid(restaurantid);
            order.setUserid(userid);
            order.setProductid(productid);
            order.setDate(new Date());
            Float cost = Float.parseFloat(maps.get(i).get("cost").toString());
            int number = Integer.parseInt(maps.get(i).get("number").toString());
            Float subtotal = Float.parseFloat(maps.get(i).get("subtotal").toString());
            order.setCost(cost);
            order.setNumber(number);
            order.setSubtotal(subtotal);
            orders.add(order);
            product.setNumber(number);
            products.add(product);
        }
        int orderid = orderService.add(orders);

        /*
            用户钱转到经理账上
         */
        float total = 0;
        for (Order order : orders) {
            total += order.getSubtotal();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("userid",userid);
        map.put("total",total);
        userService.pay(map);

        Map<String,Object> map1 = new HashMap<>();
        map1.put("orderid",orderid);
        map1.put("total",total);
        managerService.insert(map1);

        productService.consume(products);

        session.setAttribute("orderid",orderid);
        session.setAttribute("total",total);
        return "下单成功";
    }

    @ResponseBody
    @GetMapping(value = "/refreshorder")
    public List<Map> refreshorder(HttpSession session){
        int orderid = Integer.parseInt(session.getAttribute("orderid").toString());
        List<Order> orderList = orderService.selectById(orderid);
        List<Map> maps = new ArrayList<>();
        for (int i=0;i<orderList.size();i++){
            int productid = orderList.get(i).getProductid();
            int restaurantid = orderList.get(i).getRestaurantid();
            String restaurantname = resService.selectById(restaurantid).getRestaurantname();
            String productname = productService.selectById(productid).getProductname();
            Map map = new HashMap();
            map.put("orderid",orderid);
            map.put("restaurantname",restaurantname);
            map.put("productname",productname);
            map.put("cost",orderList.get(i).getCost());
            map.put("number",orderList.get(i).getNumber());
            map.put("subtotal",orderList.get(i).getSubtotal());
            maps.add(map);
        }
        return maps;
    }

    /**
     * 确认收货，订单status=1，经理抽成后打进餐厅账户
     */
    @PostMapping(value = "/order_confirm")
    @ResponseBody
    public String order_confirm(HttpSession session){
        int orderid = Integer.parseInt(session.getAttribute("orderid").toString());
        orderService.confirm(orderid);

        int restaurantid = Integer.parseInt(session.getAttribute("restaurantid").toString());
        Float total = Float.parseFloat(session.getAttribute("total").toString());
        float pay = total*9/10;
        Map<String,Object> map = new HashMap<>();
        map.put("restaurantid",restaurantid);
        map.put("total",pay);
        resService.pay(map);

        managerService.confirm(orderid);

        session.removeAttribute("total");
        return "确认收货";
    }

    /**
     * 取消订单，根据时间来确定赔款策略
     */
    @PostMapping(value = "/order_cancel")
    @ResponseBody
    public String order_cancel(@RequestBody int time, HttpSession session){
        int orderid = Integer.parseInt(session.getAttribute("orderid").toString());
        orderService.cancel(orderid);

        int userid = Integer.parseInt(session.getAttribute("userid").toString());
        int restaurantid = Integer.parseInt(session.getAttribute("restaurantid").toString());
        Float total = Float.parseFloat(session.getAttribute("total").toString());
        Map<String,Object> map = new HashMap<>();
        map.put("userid",userid);
        map.put("restaurantid",restaurantid);
        map.put("total",total);
        if (time>90){
            userService.cancel(map);
        }else {
            resService.pay(map);
        }

        managerService.cancel(orderid);

        return "取消订单";
    }

    @GetMapping(value = "/confirm_refresh")
    @ResponseBody
    public List<Map> confirm_refresh(HttpSession session){
        List<Map> maps = new ArrayList<>();
        int userid = Integer.parseInt(session.getAttribute("userid").toString());
        List<Order> orders = orderService.getUserOrder1(userid);
        for (Order order : orders) {
            int orderid = order.getOrderid();
            int restaurantid = order.getRestaurantid();
            int productid = order.getProductid();
            String restaurantname = resService.selectById(restaurantid).getRestaurantname();
            String productname = productService.selectById(productid).getProductname();
            Float cost = order.getCost();
            int number = order.getNumber();
            Float subtotal = order.getSubtotal();
            Date date = order.getDate();
            Map<String,Object> map = new HashMap<>();
            map.put("orderid",orderid);
            map.put("restaurantname",restaurantname);
            map.put("productname",productname);
            map.put("number",number);
            map.put("subtotal",subtotal);
            map.put("date",date);
            maps.add(map);
        }

        return maps;
    }

    @GetMapping(value = "/cancel_refresh")
    @ResponseBody
    public List<Map> cancel_refresh(HttpSession session){
        List<Map> maps = new ArrayList<>();
        int userid = Integer.parseInt(session.getAttribute("userid").toString());
        List<Order> orders = orderService.getUserOrder2(userid);
        for (Order order : orders) {
            int orderid = order.getOrderid();
            int restaurantid = order.getRestaurantid();
            int productid = order.getProductid();
            String restaurantname = resService.selectById(restaurantid).getRestaurantname();
            String productname = productService.selectById(productid).getProductname();
            Float cost = order.getCost();
            int number = order.getNumber();
            Float subtotal = order.getSubtotal();
            Date date = order.getDate();
            Map<String,Object> map = new HashMap<>();
            map.put("orderid",orderid);
            map.put("restaurantname",restaurantname);
            map.put("productname",productname);
            map.put("number",number);
            map.put("subtotal",subtotal);
            map.put("date",date);
            maps.add(map);
        }

        return maps;
    }

    @GetMapping(value = "/timeChart")
    @ResponseBody
    public Map timeChart(HttpSession session){
        int userid = Integer.parseInt(session.getAttribute("userid").toString());
        List<Order> orders = orderService.getUserOrder1(userid);
        Map<String,Float> map = new HashMap<>();
        map.put("Jan",0.00f);
        map.put("Feb",0.00f);
        map.put("Mar",0.00f);
        map.put("Apr",0.00f);
        map.put("May",0.00f);
        map.put("Jun",0.00f);
        map.put("Jul",0.00f);
        map.put("Aug",0.00f);
        map.put("Sep",0.00f);
        map.put("Oct",0.00f);
        map.put("Nov",0.00f);
        map.put("Dec",0.00f);
        for (Order order : orders) {
            Date date = order.getDate();
            Float subtotal = order.getSubtotal();
            if (date.getMonth()==0){
                Float total = map.get("Jan");
                total +=subtotal;
                map.put("Jan",total);
            }else if (date.getMonth()==1){
                Float total = map.get("Feb");
                total +=subtotal;
                map.put("Feb",total);
            }else if (date.getMonth()==2){
                Float total = map.get("Mar");
                total +=subtotal;
                map.put("Mar",total);
            }else if (date.getMonth()==3){
                Float total = map.get("Apr");
                total +=subtotal;
                map.put("Apr",total);
            }else if (date.getMonth()==4){
                Float total = map.get("May");
                total +=subtotal;
                map.put("May",total);
            }else if (date.getMonth()==5){
                Float total = map.get("Jun");
                total +=subtotal;
                map.put("Jun",total);
            }else if (date.getMonth()==6){
                Float total = map.get("Jul");
                total +=subtotal;
                map.put("Jul",total);
            }else if (date.getMonth()==7){
                Float total = map.get("Aug");
                total +=subtotal;
                map.put("Aug",total);
            }else if (date.getMonth()==8){
                Float total = map.get("Sep");
                total +=subtotal;
                map.put("Sep",total);
            }else if (date.getMonth()==9){
                Float total = map.get("Oct");
                total +=subtotal;
                map.put("Oct",total);
            }else if (date.getMonth()==10){
                Float total = map.get("Nov");
                total +=subtotal;
                map.put("Nov",total);
            }else if (date.getMonth()==11){
                Float total = map.get("Dec");
                total +=subtotal;
                map.put("Dec",total);
            }
        }
        return map;
    }
}
