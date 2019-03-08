package com.wbe.j2ee.service;

import com.wbe.j2ee.entity.Order;

import java.util.List;

public interface OrderService {
    void add(Order[] orders);

    int getOrder();

    List<Order> selectById(int orderid);

    void confirm(int orderid);

    void cancel(int orderid);
}
