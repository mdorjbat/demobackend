package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Recipe;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService{
    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository  categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setRecipeRepository(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategory(Long categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            return category.get();
        }else {
            throw new RuntimeException("category with id " + categoryId + " not found");
        }
    }

    public Category createCategory(Category categoryObject){
        Category category = categoryRepository.findByName(
                categoryObject.getName());
        if(category != null){
            throw new RuntimeException("category with name " + category.getName()
            + " exist");
        }else{
            return categoryRepository.save(categoryObject);
        }
    }

    public Category updateCategory(Long categoryId, Category categoryObject){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            if(categoryObject.getName().equals(category.get().getName())){
                throw new RuntimeException("category with name " + category.get().getName()
                + " already exist");
            }else{
               Category updateCategory = categoryRepository.findById(categoryId).get();
               updateCategory.setName(categoryObject.getName());
               updateCategory.setDescription(categoryObject.getDescription());
               return categoryRepository.save(updateCategory);
            }
        }else{
            throw new RuntimeException("category with name " + categoryObject.getName()
            + " not found");
        }
    }

    public Category deleteCategory(Long categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            categoryRepository.deleteById(categoryId);
            return category.get();
        }else{
            throw new RuntimeException("category with id " + categoryId + " not found");
        }
    }

    public List<Recipe> getCategoryRecipes(Long categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            return category.get().getRecipeList();
        }else{
            throw new RuntimeException("category with id " + categoryId + "not found");
        }
    }

    public Recipe getCategoryRecipe(Long categoryId, Long recipeId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            Optional<Recipe> recipe = recipeRepository.findByCategoryId(categoryId)
                    .stream().filter(p -> p.getId().equals(recipeId)).findFirst();
            if (recipe.isPresent()){
                return recipe.get();
            }else{
                throw new RuntimeException("recipe with id " + recipeId + " not found");
            }
        }else{
            throw new RuntimeException("category with id " + categoryId + " not found");
        }
    }

    public Recipe createCategoryRecipe(Long categoryId, Recipe recipObject){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            recipObject.setCategory(category.get());
            return recipeRepository.save(recipObject);
        }else{
            throw new RuntimeException("category with id " + categoryId + " not found");
        }
    }

    public Recipe updateCategoryRecipe(Long categoryId, Long recipeId, Recipe recipeObject){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            Optional<Recipe> recipe = recipeRepository.findByCategoryId(categoryId)
                    .stream().filter(p ->p.getId().equals(recipeId)).findFirst();
            if(recipe.isPresent()){
                recipe.get().setName(recipeObject.getName());
                recipe.get().setIngredients(recipeObject.getIngredients());
                recipe.get().setSteps(recipeObject.getIngredients());
                recipe.get().setTime(recipeObject.getTime());
                recipe.get().setPortions(recipeObject.getPortions());
                return recipeRepository.save(recipe.get());
            }else{
                throw new RuntimeException("recipe with id " + recipeId + " not found");
            }
        }else {
            throw new RuntimeException("category with id " + categoryId + " not found");
        }
    }

    public void deleteCategoryRecipe(Long categoryId, Long recipeId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            Optional<Recipe> recipe = recipeRepository.findByCategoryId(categoryId)
                    .stream().filter(p -> p.getId().equals(recipeId)).findFirst();
            if(recipe.isPresent()){
                recipeRepository.deleteById(recipe.get().getId());
                System.out.println("recipe with id " + recipe.get().getId() + "successfully deleted");
            }
        }else{
            throw new RuntimeException("category with id " + categoryId + " not found");
        }
    }
}
