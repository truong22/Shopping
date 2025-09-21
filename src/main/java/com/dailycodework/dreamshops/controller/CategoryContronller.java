package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.Exception.AlreadyExistsException;
import com.dailycodework.dreamshops.Exception.ResourcetNotFoundException;
import com.dailycodework.dreamshops.entity.Category;
import com.dailycodework.dreamshops.reponse.ApiReponse;
import com.dailycodework.dreamshops.service.ICategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryContronller {
    private final ICategoryService categoryService;

    @GetMapping("/{id}/category")
    public ResponseEntity<ApiReponse>getCategoryById(@PathVariable Long id){
        try{
            Category category=categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiReponse("id",category));
        }catch (ResourcetNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiReponse("not found",null));
        }
    }
    @GetMapping("name/{name}/category")
    public ResponseEntity<ApiReponse>getCategoryByName(@PathVariable String name){
        try{
            Category category=categoryService.getCategorybyName(name);
            return ResponseEntity.ok(new ApiReponse("name",category));
        }catch (ResourcetNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiReponse("not found",null));
        }
    }
    @GetMapping("/all/category")
    public ResponseEntity<ApiReponse>getCategoryByAll(){
        try{
           List<Category> category=categoryService.getCategoryByAll();
           return ResponseEntity.ok(new ApiReponse("all",category));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiReponse("Error",INTERNAL_SERVER_ERROR));
        }
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiReponse>categoryDelete(@PathVariable Long id){
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiReponse("delete ok",null));
        }catch (ResourcetNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiReponse("not id",null));
        }
    }
    @PostMapping("/add/category")
    public ResponseEntity<ApiReponse>addCategory(@RequestBody Category name){
        try {
            Category category=categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiReponse("Success",category));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT).body(new ApiReponse("not name",null));
        }
    }
    @PutMapping("/update/category/{id}")
    public ResponseEntity<ApiReponse>updateCategory(@RequestBody Category name,@PathVariable Long id){
        try{
            Category category=categoryService.updateCategory(name,id);
            return ResponseEntity.ok(new ApiReponse("update ok",category));
        }catch (ResourcetNotFoundException e){
            return  ResponseEntity.status(NOT_FOUND).body(new ApiReponse("not update",null));
        }
    }

}
