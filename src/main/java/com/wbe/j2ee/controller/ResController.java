package com.wbe.j2ee.controller;

import com.wbe.j2ee.entity.Order;
import com.wbe.j2ee.entity.Product;
import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.service.OrderService;
import com.wbe.j2ee.service.ProductService;
import com.wbe.j2ee.service.ResService;
import com.wbe.j2ee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("/res")
public class ResController {

    private final ResService resService;

    private final ProductService productService;

    private final OrderService orderService;

    private final UserService userService;

    @Autowired
    public ResController(ResService resService, ProductService productService, OrderService orderService,UserService userService) {
        this.resService = resService;
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }

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
     * 跳转到历史订单页面，需要先登录，即验证session中是否有restaurantUUID属性
     */
    @RequestMapping(value = "/orderlist")
    public String orderlist(HttpSession session){
        if (session.getAttribute("restaurantUUID")!=null){
            return "restaurant/orderlist";
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

    /**
     * 获取确认收货的订单列表
     * @param session 获取session里的餐厅id
     * @return 该餐厅确认被收货的商品列表
     */
    @GetMapping(value = "/confirm_refresh")
    @ResponseBody
    public List<Map> confirm_refresh(HttpSession session){
        List<Map> maps = new ArrayList<>();
        int restaurantid = Integer.parseInt(session.getAttribute("restaurantid").toString());
        List<Order> orders = orderService.getResOrder1(restaurantid);
        for (Order order : orders) {
            int orderid = order.getOrderid();
            int userid = order.getUserid();
            int productid = order.getProductid();
            String username = userService.selectById(userid).getUsername();
            String productname = productService.selectById(productid).getProductname();
            int number = order.getNumber();
            Float subtotal = order.getSubtotal();
            Date date = order.getDate();
            Map<String,Object> map = new HashMap<>();
            map.put("orderid",orderid);
            map.put("username",username);
            map.put("productname",productname);
            map.put("number",number);
            map.put("subtotal",subtotal);
            map.put("date",date);
            maps.add(map);
        }
        return maps;
    }

    /**
     * 获取确认被退货的订单列表
     * @param session 获取session里的餐厅id
     * @return 该餐厅确认被退货的商品列表
     */
    @GetMapping(value = "/cancel_refresh")
    @ResponseBody
    public List<Map> cancel_refresh(HttpSession session){
        List<Map> maps = new ArrayList<>();
        int restaurantid = Integer.parseInt(session.getAttribute("restaurantid").toString());
        List<Order> orders = orderService.getResOrder2(restaurantid);
        for (Order order : orders) {
            int orderid = order.getOrderid();
            int userid = order.getUserid();
            int productid = order.getProductid();
            String username = userService.selectById(userid).getUsername();
            String productname = productService.selectById(productid).getProductname();
            int number = order.getNumber();
            Float subtotal = order.getSubtotal();
            Date date = order.getDate();
            Map<String,Object> map = new HashMap<>();
            map.put("orderid",orderid);
            map.put("username",username);
            map.put("productname",productname);
            map.put("number",number);
            map.put("subtotal",subtotal);
            map.put("date",date);
            maps.add(map);
        }
        return maps;
    }

    /**
     * 餐厅在12个月每个月的收益图表
     * @param session 获取session里的餐厅id
     * @return 存有每个月的收益的map
     */
    @GetMapping(value = "/timeChart")
    @ResponseBody
    public Map timeChart(HttpSession session){
        int restarantid = Integer.parseInt(session.getAttribute("restaurantid").toString());
        List<Order> orders = orderService.getResOrder1(restarantid);
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

    /**
     * 餐厅各种商品的收益图表
     * @param session 获取session里的餐厅id
     * @return 存有各种商品的收益的map
     */
    @GetMapping(value = "/typeChart")
    @ResponseBody
    public Map typeChart(HttpSession session){
        int restaurantid = Integer.parseInt(session.getAttribute("restaurantid").toString());
        List<Order> orders = orderService.getResOrder1(restaurantid);
        Map<String,Float> map = new HashMap<>();
        map.put("meal",0.00f);
        map.put("dish",0.00f);
        for (Order order : orders){
            Product product = productService.selectById(order.getProductid());
            String type = product.getType();
            Float subtotal = order.getSubtotal();
            if (type.equals("主食")){
                Float total = map.get("meal");
                total += subtotal;
                map.put("meal",total);
            }else if (type.equals("菜品")){
                Float total = map.get("dish");
                total += subtotal;
                map.put("dish",total);
            }
        }
        Float sum1 = map.get("meal");
        Float sum2 = map.get("dish");
        DecimalFormat decimalFormat = new DecimalFormat("###.00");
        Float percent1 = Float.parseFloat(decimalFormat.format(sum1/(sum1+sum2)));
        Float percetn2 = Float.parseFloat(decimalFormat.format(sum2/(sum1+sum2)));
        map.put("meal",percent1);
        map.put("dish",percetn2);
        return map;
    }
}
