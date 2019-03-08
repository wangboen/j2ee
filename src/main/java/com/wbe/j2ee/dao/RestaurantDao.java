package com.wbe.j2ee.dao;

import com.wbe.j2ee.entity.Restaurant;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface RestaurantDao {
    /**
     * 避免餐厅重名
     */
    Restaurant selectByName(String restaurantname);

    /**
     * 根据restaurantid查询餐厅
     */
    Restaurant selectById(int restaurantid);

    /**
     * 餐厅登录，根据生成的restaurantUUID查找账户
     */
    Restaurant login(String restaurantUUID);

    /**
     * 注册餐厅，初始化一个restaurantUUID
     */
    void register(String restaurantUUID);

    /**
     * 修改餐厅信息，status设置为0，需要审批
     */
    void modify(Restaurant restaurant);

    /**
     * 获取未审批的餐厅列表，status为0
     */
    List<Restaurant> getResList0();

    /**
     * 获取审批通过正在运营的餐厅列表，status为1
     */
    List<Restaurant> getResList1();

    /**
     * 审批通过餐厅修改信息
     */
    void confirm(int restaurantid);

    /**
     * 经理将预付款打到餐厅账上
     */
    void pay(Map<String,Object> map);

    /**
     * 根据用户所在地查找附近正在运营的餐厅
     * @return
     */
    List<Restaurant> searchByAdress(String address);
}
