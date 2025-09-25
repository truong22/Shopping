package com.dailycodework.dreamshops.service.Product;

import com.dailycodework.dreamshops.Request.AddProductRequest;
import com.dailycodework.dreamshops.Request.UpdateProductRequest;
import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.entity.Product;

import java.util.List;

public interface IproductService {
    Product addProduct(AddProductRequest request);
    Product updateProduct(UpdateProductRequest request,Long id);
    void  deleteProduct(Long id);
    Product getByProductById(Long id);
    List<Product> getProductAll();
    List<Product>getProductByCategory(String  category);
    List<Product>getProductByBrand(String brand);
    List<Product>getProductByName(String name);
    List<Product>getProductByCategoryAndBrand(String category,String brand);
    List<Product>getProductByNameAndBrand(String name,String brand);
    Long countProductByBrandAndName(String name,String brand);

    ProductDto ConverToDto(Product product);
    List<ProductDto> getconvertedProduct(List<Product>products);
}
