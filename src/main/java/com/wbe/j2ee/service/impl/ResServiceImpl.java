package com.wbe.j2ee.service.impl;


import com.wbe.j2ee.dao.RestaurantDao;
import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ResServiceImpl implements ResService {
    @Autowired
    private RestaurantDao restaurantDao;

    /**
     * 餐厅登录
     * @param restaurant
     */
    @Override
    public Restaurant login(Restaurant restaurant) {
        Restaurant restaurant1 = restaurantDao.Login(restaurant);
        if (restaurant1 !=null){
            return restaurant1;
        }
        return null;
    }

    /**
     * 判断修改后的餐厅名是否被占用
     * @param restaurant 获取修改后的餐厅名
     * @return 返回重名的餐厅相关信息
     */
    @Override
    public Restaurant selectByName(Restaurant restaurant) {
        return restaurantDao.selectByName(restaurant);
    }

    /**
     * 餐厅注册
     * @return Restarant
     */
    @Override
    public Restaurant register() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.substring(0,7);
        Restaurant restaurant = new Restaurant();
        restaurant.setUUID(uuid);
        restaurantDao.Register(restaurant);
        Restaurant restaurant1 = restaurantDao.Login(restaurant);
        return restaurant1;
    }

    /**
     * 修改餐厅信息
     * @param restaurant
     */
    @Override
    public void modify(Restaurant restaurant) {
        restaurantDao.UpdateRes(restaurant);
        return;
    }

    /**
     * 返回所有待审批的餐厅修改信息
     * @return List<Restaurant>
     */
    @Override
    public List<Restaurant> getResList0() {
        return restaurantDao.getResList0();
    }

    /**
     * 审批通过餐厅修改信息
     * @param restaurant
     */
    @Override
    public void confirm(Restaurant restaurant) {
        restaurantDao.Confirm(restaurant);
        return;
    }

    /**
     * 返回所有通过了审批的餐厅信息
     * @return List<Restaurant>
     */
    @Override
    public List<Restaurant> getResList1() {
        return restaurantDao.getResList1();
    }

    @Override
    public List<Restaurant> search(String address) {
        return restaurantDao.search(address);
    }
}
