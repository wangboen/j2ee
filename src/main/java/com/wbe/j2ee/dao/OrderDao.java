package com.wbe.j2ee.dao;

import com.wbe.j2ee.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderDao {
    /**
     * 添加订单信息
     */
    void add(Order order);

    /**
     * 获取最新的订单
     */
    Integer max();

    /**
     * 根据orderid查找订单信息
     */
    List<Order> selectById(int orderid);

    /**
     * 确认订单送达
     */
    void confirm(Map<String,Object> map);

    /**
     * 取消订单
     */
    void cancel(Map<String,Object> map);

    /**
     * 获取用户已完成订单列表
     */
    List<Order> getUserOrder1(int userid);

    /**
     * 获取用户已退订订单列表
     */
    List<Order> getUserOrder2(int userid);

    /**
     * 获取餐馆已配送订单列表
     */
    List<Order> getResOrder1(int restaurantid);

    /**
     * 获取餐厅遭退订订单列表
     */
    List<Order> getResOrder2(int restaurantid);
}
