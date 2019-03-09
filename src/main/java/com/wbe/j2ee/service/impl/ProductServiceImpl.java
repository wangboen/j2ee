package com.wbe.j2ee.service.impl;

import com.wbe.j2ee.dao.ProductDao;
import com.wbe.j2ee.entity.Product;
import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void add(Product product) {
        Product product1 = productDao.selectByName(product);
        if(product1 != null){
            product.setNumber(product.getNumber()+product1.getNumber());
            productDao.update(product);
        }else {
            productDao.add(product);
        }
    }

    @Override
    public List<Product> getProList(int restaurantid) {
        return productDao.getProList(restaurantid);
    }

    @Override
    public void consume(List<Product> productList) {
        for (Product product:productList){
            Product product1 = productDao.selectByName(product);
            product1.setNumber(product1.getNumber()-product.getNumber());
            product1.setDate(new Date());
            productDao.update(product1);
        }
    }

    @Override
    public Product selectByName(Product product) {
        return productDao.selectByName(product);
    }

    @Override
    public Product selectById(int productid) {
        return productDao.selectById(productid);
    }
}
