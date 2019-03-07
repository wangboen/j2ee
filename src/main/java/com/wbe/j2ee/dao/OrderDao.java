package com.wbe.j2ee.dao;

import com.wbe.j2ee.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {
    /**
     * 添加订单
     * @param order 要添加的订单的相关信息
     */
    void add(Order order);

    /**
     * 获取最大的orderid
     * @return 最大的orderid
     */
    Integer max();

    List<Order> selectById(int orderid);

    void confirm(Order order);

    void cancel(Order order);
}
