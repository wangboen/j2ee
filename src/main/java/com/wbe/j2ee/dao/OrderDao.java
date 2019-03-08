package com.wbe.j2ee.dao;

import com.wbe.j2ee.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    void confirm(int orderid);

    /**
     * 取消订单
     */
    void cancel(int orderid);
}
