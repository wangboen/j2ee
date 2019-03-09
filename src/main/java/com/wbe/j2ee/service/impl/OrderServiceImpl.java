package com.wbe.j2ee.service.impl;

import com.wbe.j2ee.dao.OrderDao;
import com.wbe.j2ee.entity.Order;
import com.wbe.j2ee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public int add(List<Order> orders) {
        int orderid = 1;
        if (orderDao.max()!=null){
            orderid = orderDao.max()+1;
        }
        for (Order order : orders) {
            order.setOrderid(orderid);
            orderDao.add(order);
        }
        return orderid;
    }

    @Override
    public int getOrder() {
        return orderDao.max();
    }

    @Override
    public List<Order> selectById(int orderid) {
        return orderDao.selectById(orderid);
    }

    @Override
    public void confirm(int orderid) {
        orderDao.confirm(orderid);
    }

    @Override
    public void cancel(int orderid) {
        orderDao.cancel(orderid);
    }
}
