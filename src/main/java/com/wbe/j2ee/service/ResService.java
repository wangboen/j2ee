package com.wbe.j2ee.service;

import com.wbe.j2ee.entity.Restaurant;

import java.util.List;
import java.util.Map;

public interface ResService {
    Restaurant selectByName(String restaurantname);

    Restaurant selectById(int restaurantid);

    Restaurant login(String restaurantUUID);

    Restaurant register();

    void modify(Restaurant restaurant);

    List<Restaurant> getResList0();

    void confirm(int restaurantid);

    void pay(Map<String,Object> map);

    List<Restaurant> searchByAdress(String address);

    int count();
}
