package com.dailycodework.dreamshops.service.Product;

import com.dailycodework.dreamshops.Request.AddProductRequest;
import com.dailycodework.dreamshops.Request.UpdateProductRequest;
import com.dailycodework.dreamshops.entity.Product;

import java.util.List;

public interface IproductService {
    Product addProduct(AddProductRequest request);
    Product updateProduct(UpdateProductRequest request,Long id);
    void  deleteProduct(Long id);
    Product getByProductById(Long id);
    List<Product> getProductAll();
    List<Product>getProductByCategory(String  nameCategory);
    List<Product>getProductByBrand(String brand);
    List<Product>getProductByName(String name);
    List<Product>getProductByCategoryAndBrand(String nameCategory,String brand);
    List<Product>getProductByNameAndCategory(String name,String nameCategory);
    Long countProductByBrandAndName(String name,String brand);
}
