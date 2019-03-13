package com.wbe.j2ee.service.impl;


import com.wbe.j2ee.dao.RestaurantDao;
import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ResServiceImpl implements ResService {
    private final RestaurantDao restaurantDao;

    @Autowired
    public ResServiceImpl(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    @Override
    public Restaurant selectByName(String restaurantname) {
        Restaurant restaurant = restaurantDao.selectByName(restaurantname);
        if (restaurant != null) return restaurant;
        return null;
    }

    @Override
    public Restaurant selectById(int restaurantid) {
        Restaurant restaurant = restaurantDao.selectById(restaurantid);
        if (restaurant != null) return restaurant;
        return null;
    }

    @Override
    public Restaurant login(String restaurantUUID) {
        Restaurant restaurant = restaurantDao.login(restaurantUUID);
        if (restaurant != null) return restaurant;
        return null;
    }

    @Override
    public Restaurant register() {
        String restaurantUUID = UUID.randomUUID().toString();
        restaurantUUID = restaurantUUID.substring(0,7);
        restaurantDao.register(restaurantUUID);
        Restaurant restaurant = restaurantDao.login(restaurantUUID);
        if (restaurant != null) return restaurant;
        return null;
    }

    @Override
    public void modify(Restaurant restaurant) {
        restaurantDao.modify(restaurant);
    }

    @Override
    public List<Restaurant> getResList0() {
        List<Restaurant> restaurantList = restaurantDao.getResList0();
        if (restaurantList != null) return restaurantList;
        return null;
    }

    @Override
    public void confirm(int restaurantid) {
        restaurantDao.confirm(restaurantid);
    }

    @Override
    public void pay(Map<String, Object> map) {
        restaurantDao.pay(map);
    }

    @Override
    public List<Restaurant> searchByAdress(String address) {
        List<Restaurant> restaurantList = restaurantDao.searchByAdress(address);
        if (restaurantList != null) return restaurantList;
        return null;
    }

    @Override
    public int count() {
        return restaurantDao.count();
    }
}
