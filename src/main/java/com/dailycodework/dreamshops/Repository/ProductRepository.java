package com.dailycodework.dreamshops.Repository;

import com.dailycodework.dreamshops.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findProductByCategory(String  nameCategory);
    List<Product>findProductByBrand(String brand);
    List<Product>findProductByName(String name);
    List<Product>findProductByCategoryAndBrand(String nameCategory,String brand);
    List<Product>findProductByNameAndCategory(String name,String nameCategory);
    Long countProductByNameAndBrand(String name,String brand);

}
