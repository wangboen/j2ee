package com.wbe.j2ee.service;

import com.wbe.j2ee.entity.Product;

import javax.swing.*;
import java.util.List;

public interface ProductService {
    /**
     * 添加商品
     * @param product
     */
    void add(Product product);

    /**
     * 获取商品列表，根据餐厅uuid来获取餐厅商品列表
     * @param resuuid 餐厅uuid
     * @return 餐厅商品列表
     */
    List<Product> getProList(String resuuid);

    void consume(List<Product> productList);
}
