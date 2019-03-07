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

    @Autowired
    private OrderDao orderDao;

    @Override
    public void add(Order[] orders) {
        int orderid = 1;
        if (orderDao.max()!=null){
            orderid = orderDao.max()+1;
        }
        for (int i=0;i<orders.length;i++){
            orders[i].setOrderid(orderid);
            orderDao.add(orders[i]);
        }
    }

    @Override
    public int getOrder() {
        return orderDao.max();
    }

    @Override
    public List<Order> selectById(int orderid) {
        return orderDao.selectById(orderid);
    }
}
