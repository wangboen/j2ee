package com.wbe.j2ee.service;

import com.wbe.j2ee.entity.Product;
import java.util.List;

public interface ProductService {
    void add(Product product);

    List<Product> getProList(int restaurantid);

    void consume(List<Product> productList);
}
