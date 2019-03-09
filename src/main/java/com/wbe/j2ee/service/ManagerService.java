package com.wbe.j2ee.service;

import java.util.Map;

public interface ManagerService {
    void insert(Map map);

    void confirm(int orderid);

    void cancel(int orderid);
}
