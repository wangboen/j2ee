package com.wbe.j2ee.service.impl;

import com.wbe.j2ee.dao.ProductDao;
import com.wbe.j2ee.entity.Product;
import com.wbe.j2ee.entity.Restaurant;
import com.wbe.j2ee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public void add(Product product) {
        Product product1 = productDao.selectByName(product);
        if(product1!=null){
            product.setNumber(product.getNumber()+product1.getNumber());
            productDao.Update(product);
        }else {
            productDao.Add(product);
        }
    }

    @Override
    public List<Product> getProList(String resuuid) {
        return productDao.getProList(resuuid);
    }

    @Override
    public void consume(List<Product> productList) {
        for (Product product:productList){
            Product product1 = productDao.selectByName(product);
            product1.setNumber(product1.getNumber()-product.getNumber());
            productDao.Update(product1);
        }
    }
}
