package com.dailycodework.dreamshops.Repository;

import com.dailycodework.dreamshops.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findProductByCategory_Name(String  category);
    List<Product>findProductByBrand(String brand);
    List<Product>findProductByName(String name);
    List<Product>findProductByCategory_NameAndBrand(String category,String brand);
    List<Product>findProductByBrandAndName(String brand,String name);
    Long countProductByNameAndBrand(String name,String brand);

}
