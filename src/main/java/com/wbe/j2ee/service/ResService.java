package com.wbe.j2ee.service;

import com.wbe.j2ee.entity.Restaurant;

import java.util.List;

public interface ResService {
    /**
     * 餐厅登录
     * @param restaurant 餐厅uuid
     */
    Restaurant login(Restaurant restaurant);

    /**
     * 判断修改后的餐厅名是否被占用
     * @param restaurant 获取修改后的餐厅名
     * @return 返回重名的餐厅相关信息
     */
    Restaurant selectByName(Restaurant restaurant);

    /**
     * 餐厅注册
     * @return Restarant
     */
    Restaurant register();

    /**
     * 修改餐厅信息
     * @param restaurant 修改后的餐厅相关信息
     */
    void modify(Restaurant restaurant);

    /**
     * 返回所有待审批的餐厅修改信息
     * @return List<Restaurant>
     */
    List<Restaurant> getResList0();

    /**
     * 审批通过餐厅修改信息
     * @param restaurant 获取餐厅uuid
     */
    void confirm(Restaurant restaurant);

    /**
     * 返回所有通过了审批的餐厅信息
     * @return List<Restaurant>
     */
    List<Restaurant> getResList1();

    List<Restaurant> search(String address);
}
