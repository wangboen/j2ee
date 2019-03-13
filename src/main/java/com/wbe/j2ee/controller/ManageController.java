package com.wbe.j2ee.controller;

import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.service.ManagerService;
import com.wbe.j2ee.service.ResService;
import com.wbe.j2ee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/manage")
public class ManageController {
    @Autowired
    private ResService resService;

    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;

    @RequestMapping(value = "/list")
    public String test(){
        return "manage/list";
    }

    @RequestMapping(value = "/statistics")
    public String statistics(){
        return "manage/statistics";
    }

    @ResponseBody
    @RequestMapping(value = "/refresh")
    public List<Restaurant> manage(){
        return resService.getResList0();
    }

    @PutMapping(value = "/confirm")
    public String confirm(@RequestBody Restaurant restaurant){
        resService.confirm(restaurant.getRestaurantid());
        return "manage/list";
    }

    @GetMapping(value = "/count")
    @ResponseBody
    public Map<String,Object> count(){
        Map<String,Object> map = new HashMap<>();
        int user_number = userService.count();
        int res_number = resService.count();
        Float account = managerService.max();
        map.put("user",user_number);
        map.put("res",res_number);
        map.put("account",account);
        return map;
    }
}
