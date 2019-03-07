package com.wbe.j2ee.dao;

import com.wbe.j2ee.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {
    /**
     * 添加商品
     * @param product 要添加的商品的相关信息
     */
    void Add(Product product);

    void Update(Product product);

    /**
     * 根据餐厅UUID获取商品列表
     * @param resuuid 餐厅UUID
     * @return 商品列表
     */
    List<Product> getProList(String resuuid);

    Product selectByName(Product product);
}
