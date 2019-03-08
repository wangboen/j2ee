package com.wbe.j2ee.controller;

import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/manage")
public class ManageController {
    @Autowired
    private ResService resService;

    @RequestMapping(value = "/list")
    public String test(){
        return "manage/list";
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
}
