package com.dailycodework.dreamshops.service;

import com.dailycodework.dreamshops.Exception.AlreadyExistsException;
import com.dailycodework.dreamshops.Exception.ResourcetNotFoundException;
import com.dailycodework.dreamshops.Repository.CategoryRepository;
import com.dailycodework.dreamshops.entity.Category;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->
            new ResourcetNotFoundException("not id found")
        );
    }

    @Override
    public Category getCategorybyName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    @Override
    public List<Category> getCategoryByAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Long id) {
     categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,()->{
            throw new ResourcetNotFoundException("not id found");
        });

    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c->!categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(()->new AlreadyExistsException("da ton tai"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(categoryOld->{categoryOld.setName(category.getName());
            return categoryRepository.save(categoryOld); }).orElseThrow(()->new ResourcetNotFoundException("not id found"));
}}
