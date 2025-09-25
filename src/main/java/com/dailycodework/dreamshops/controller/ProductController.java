package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.Exception.ResourcetNotFoundException;
import com.dailycodework.dreamshops.Request.AddProductRequest;
import com.dailycodework.dreamshops.Request.UpdateProductRequest;
import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.reponse.ApiReponse;
import com.dailycodework.dreamshops.service.Product.IproductService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IproductService productService;
    @GetMapping("/all")
    public ResponseEntity<ApiReponse>getProductByAll(){
            List<Product> product=productService.getProductAll();
            List<ProductDto> conventedProduct=productService.getconvertedProduct(product);
            return ResponseEntity.ok(new ApiReponse("Success",conventedProduct));
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<ApiReponse>getProductById(@PathVariable Long id){
        try{
            Product product=productService.getByProductById(id);
            ProductDto productDto=productService.ConverToDto(product);
            return ResponseEntity.ok(new ApiReponse(" Id Success",productDto));
        }catch (ResourcetNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiReponse(e.getMessage(),null));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiReponse>addProduct(@RequestBody AddProductRequest request)
    {
        try {
            Product product=productService.addProduct(request);
            ProductDto productDto=productService.ConverToDto(product);
            return ResponseEntity.ok(new ApiReponse("Success",productDto));
         }catch (Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiReponse(e.getMessage(),null));
    }
    }
    @PutMapping("/update/{id}/product")
    public ResponseEntity<ApiReponse>updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long id)
    {
        try {
            Product product=productService.updateProduct(request,id);
            ProductDto productDto=productService.ConverToDto(product);
            return ResponseEntity.ok(new ApiReponse("Success",productDto));
        }catch (ResourcetNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiReponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiReponse>deleteProduct(@PathVariable Long id)
    {
        try {
          productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiReponse("delete ok",null));
        }catch (ResourcetNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiReponse(e.getMessage(),null));
        }
    }
    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiReponse> getProductByBrandAndName(@RequestParam(name = "brand") String brand, @RequestParam (name = "name") String name) {
        try {
            List<Product> products = productService.getProductByNameAndBrand(brand, name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiReponse("No products found ", null));
            }
            List<ProductDto> convertedProducts = productService.getconvertedProduct(products);
            return  ResponseEntity.ok(new ApiReponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiReponse(e.getMessage(), null));
        }
    }
    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiReponse> getProductByBrandAndCategory(@RequestParam String brand, @RequestParam String category) {
        try {
            List<Product> products = productService.getProductByCategoryAndBrand(brand, category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiReponse("No products found ", null));
            }
            List<ProductDto> convertedProducts = productService.getconvertedProduct(products);
            return  ResponseEntity.ok(new ApiReponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiReponse(e.getMessage(), null));
        }
    }
    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiReponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiReponse("No products found ", null));
            }
            List<ProductDto> convertedProducts = productService.getconvertedProduct(products);
            return  ResponseEntity.ok(new ApiReponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiReponse(e.getMessage(), null));
        }
    }@GetMapping("/product/by-brand")
    public ResponseEntity<ApiReponse> findProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiReponse("No products found ", null));
            }
            List<ProductDto> convertedProducts = productService.getconvertedProduct(products);
            return  ResponseEntity.ok(new ApiReponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiReponse(e.getMessage(), null));
        }
    }
    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiReponse> findProductByCategory(@PathVariable(name = "category") String category) {
        try {
            List<Product> products = productService.getProductByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiReponse("No products found ", null));
            }
            List<ProductDto> convertedProducts = productService.getconvertedProduct(products);
            return  ResponseEntity.ok(new ApiReponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiReponse(e.getMessage(), null));
        }
    }@GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiReponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiReponse("Product count!", productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiReponse(e.getMessage(), null));
        }
    }


}
