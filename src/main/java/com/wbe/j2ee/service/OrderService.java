package com.wbe.j2ee.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.wbe.j2ee.entity.Order;

import java.util.List;

public interface OrderService {
    void add(Order[] orders);

    int getOrder();

    List<Order> selectById(int orderid);
}
