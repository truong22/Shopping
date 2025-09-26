package com.dailycodework.dreamshops.service.Product;

import com.dailycodework.dreamshops.Exception.ResourcetNotFoundException;
import com.dailycodework.dreamshops.Repository.CategoryRepository;
import com.dailycodework.dreamshops.Repository.ImageRepository;
import com.dailycodework.dreamshops.Repository.ProductRepository;
import com.dailycodework.dreamshops.Request.AddProductRequest;
import com.dailycodework.dreamshops.Request.UpdateProductRequest;
import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.Config.Shopconfig;
import com.dailycodework.dreamshops.entity.Category;
import com.dailycodework.dreamshops.entity.Image;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.service.Category.ICategoryService;
import com.dailycodework.dreamshops.service.Image.ImageService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService  implements IproductService {
    private final ProductRepository productRepository;
    private  final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private  final ImageRepository imageRepository;
    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findCategoryByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory= new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category);
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long id) {
        return productRepository.findById(id).map(existingProduct->updateExistingProduct(request,existingProduct))
                .map(productRepository::save).orElseThrow( ()->new ResourcetNotFoundException("not id found"));
    }
    private Product updateExistingProduct(UpdateProductRequest request,Product existingProduct){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDesciption(request.getDescription());
        Category category = categoryRepository.findCategoryByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository ::delete,
                ()->{ throw new ResourcetNotFoundException("not id found");});

    }

    @Override
    public Product getByProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ResourcetNotFoundException("not id found"));
    }

    @Override
    public List<Product> getProductAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategory(String nameCategory) {
        return productRepository.findProductByCategory_Name(nameCategory);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findProductByBrand(brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findProductByCategory_NameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByNameAndBrand(String name, String brand) {
        return productRepository.findProductByBrandAndName(name,brand);
    }

    @Override
    public Long countProductByBrandAndName(String name, String brand) {
        return productRepository.countProductByNameAndBrand(name,brand);
    }

    @Override
    public List<ProductDto> getconvertedProduct(List<Product> products) {
        return products.stream().map(this::ConverToDto).toList();
    }

    public ProductDto ConverToDto(Product product){
        ProductDto productDto=modelMapper.map(product,ProductDto.class);
        List<Image>images=imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos=images.stream()
                .map(image -> {
                    ImageDto dto = modelMapper.map(image, ImageDto.class);
                    dto.setDownloadUrl("/api/v1/images/image/download/" + image.getId());
                    return dto;
                })
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }

}
