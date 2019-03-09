package com.wbe.j2ee.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ManagerDao {
    void insert(Map map);

    Float account();

    Float selectByOrder(int orderid);
}
