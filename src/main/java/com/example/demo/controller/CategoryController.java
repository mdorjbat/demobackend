package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Recipe;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/categoires")
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping(path = "/categories/{categoryI}")
    public Category getCategory(@PathVariable Long categoryId){
        return categoryService.getCategory(categoryId);
    }

    @PostMapping(path = "/categories/")
    public Category createCategory(@RequestBody Category categoryObject){
        return categoryService.createCategory(categoryObject);
    }

    @PutMapping(path = "/categories/{categoryId}")
    public Category updateCategory(@PathVariable Long categoryId, @RequestBody Category categoryObject){
        return categoryService.updateCategory(categoryId, categoryObject);
    }

    @DeleteMapping(path = "/categories/{categoryId}")
    public Category deleteCategory(@PathVariable Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping(path = "/categories/{categoryId}")
    public List<Recipe> getCategoryRecipes(@PathVariable Long categoryId){
        return categoryService.getCategoryRecipes(categoryId);
    }

    @GetMapping(path = "/categories/{categoryId}/{recipeId}")
    public Recipe getCategoryRecipe(Long categoryId, Long recipeId){
        return categoryService.getCategoryRecipe(categoryId, recipeId);
    }

    @PostMapping(path = "/categories/{categoryId}")
    public Recipe createCategoryRecipe(@PathVariable Long categoryId, @RequestBody Recipe recipeObject){
        return categoryService.createCategoryRecipe(categoryId, recipeObject);
    }

    @PutMapping(path = "/categories/{categoryId}/{recipeId}")
    public Recipe updateCategoryRecipe(@PathVariable Long categoryId, @PathVariable Long recipeId,
                                       @RequestBody Recipe recipeObject){
        return categoryService.updateCategoryRecipe(categoryId, recipeId, recipeObject);
    }

    @DeleteMapping(path = "/categories/{categoryId}/{recipeId}")
    public void deleteCategoryRecipe(@PathVariable Long categoryId, @PathVariable Long recipeId){
       categoryService.deleteCategoryRecipe(categoryId, recipeId);
    }
}
