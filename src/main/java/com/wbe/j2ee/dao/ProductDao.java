package com.wbe.j2ee.dao;

import com.wbe.j2ee.entity.Product;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductDao {
    /**
     * 添加商品
     */
    void add(Product product);

    /**
     * 更新商品类型，数量，价格，同步更新时间
     */
    void update(Product product);

    /**
     * 根据restaurantid获取商品列表
     */
    List<Product> getProList(int restaurantid);

    /**
     * 根据restaurantid和productname获取商品信息
     */
    Product selectByName(Product product);

    /**
     * 根据productid查询商品信息
     */
    Product selectById(int productid);

    /**
     * 下单后商品就被消耗
     */
    void consume(Product product);
}
