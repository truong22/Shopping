package com.dailycodework.dreamshops.service.Category;

import com.dailycodework.dreamshops.entity.Category;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(Long id);
    Category getCategorybyName(String name);
    List<Category> getCategoryByAll();
    void deleteCategory(Long id);
    Category addCategory(Category category);
    Category updateCategory(Category category,Long id);
}
