package com.wbe.j2ee.service.impl;

import com.wbe.j2ee.dao.ManagerDao;
import com.wbe.j2ee.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final ManagerDao managerDao;

    @Autowired
    public ManagerServiceImpl(ManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    @Override
    public void insert(Map map) {
        float account = 0;
        if (managerDao.account() != null){
            account = managerDao.account();
        }
        account = account +  Float.parseFloat(map.get("total").toString());
        map.put("account",account);
        map.put("date",new Date());
        managerDao.insert(map);
    }

    @Override
    public void confirm(int orderid) {
        float total = managerDao.selectByOrder(orderid);
        total = total*9/10;
        total = 0-total;
        float account = managerDao.account() +total;
        Map map = new HashMap();
        map.put("orderid",orderid);
        map.put("total",total);
        map.put("account",account);
        map.put("date",new Date());
        managerDao.insert(map);
    }

    @Override
    public void cancel(int orderid) {
        float total = managerDao.selectByOrder(orderid);
        total = 0-total;
        float account = managerDao.account() +total;
        Map map = new HashMap();
        map.put("orderid",orderid);
        map.put("total",total);
        map.put("account",account);
        map.put("date",new Date());
        managerDao.insert(map);
    }

    @Override
    public Float max() {
        return managerDao.max();
    }
}
