package com.wbe.j2ee.dao;

import com.wbe.j2ee.entity.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RestaurantDao {
    /**
     * 登录，根据uuid查询是否存在
     * @param restaurant 获取输入的uuid
     * @return 返回uuid所属的餐厅相关信息
     */
    Restaurant Login(Restaurant restaurant);

    /**
     * 判断修改后的餐厅名是否被占用
     * @param restaurant 获取修改后的餐厅名
     * @return 返回重名的餐厅相关信息
     */
    Restaurant selectByName(Restaurant restaurant);

    /**
     * 注册
     * @param restaurant 要注册的餐馆的UUID
     */
    void Register(Restaurant restaurant);

    /**
     * 修改餐厅信息，需要审核
     * @param restaurant 要修改成的餐厅相关信息
     */
    void UpdateRes(Restaurant restaurant);

    /**
     * 审批通过餐厅修改信息
     * @param restaurant 要确认审批通过的餐厅的uuid
     */
    void Confirm(Restaurant restaurant);

    /**
     * 返回所有待审批的餐厅修改信息
     * @return List<Restaurant>
     */
    List<Restaurant> getResList0();

    /**
     * 返回所有通过了审批的餐厅信息
     * @return List<Restaurant>
     */
    List<Restaurant> getResList1();

    void confirm1(Map<String,Object> map);

    List<Restaurant> search(String address);
}
