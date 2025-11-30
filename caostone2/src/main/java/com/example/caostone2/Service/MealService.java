package com.example.caostone2.Service;

import com.example.caostone2.Model.Admin;
import com.example.caostone2.Model.Category;
import com.example.caostone2.Model.Meal;
import com.example.caostone2.Repository.AdminRepository;
import com.example.caostone2.Repository.CategoryRepository;
import com.example.caostone2.Repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final AdminRepository adminRepository;
    private final CategoryRepository categoryRepository;

    public List<Meal> getAllMeals(){
        return mealRepository.findAll();
    }

    public String addMeal(Integer adminId,Meal meal){
        Admin admin=adminRepository.findAdminById(adminId);
        if (admin==null){
            return "The admin id is not exits"; // you are not admin
        }
        Category category=categoryRepository.findCategoryById(meal.getCategoryId());
        if (category==null){
            return "The category id is not exits"; // not found category id
        }
        mealRepository.save(meal);
        return "success"; //success
    }
    public String updateMeal(Integer adminId,Integer id,Meal meal){
        Admin admin=adminRepository.findAdminById(adminId);
        if (admin==null){
            return "The admin id is not exits"; // you are not admin
        }
        Meal oldMeal=mealRepository.findMealById(id);
        if (oldMeal==null){
            return "The meal id is not exits"; // not found meal id
        }
        Category category=categoryRepository.findCategoryById(meal.getCategoryId());
        if (category==null){
            return "The category id is not exits"; // not found category id
        }
        oldMeal.setCategoryId(meal.getCategoryId());
        oldMeal.setName(meal.getName());
        oldMeal.setDescription(meal.getDescription());
        oldMeal.setCalories(meal.getCalories());
        oldMeal.setProtein(meal.getProtein());
        oldMeal.setCarb(meal.getCarb());
        oldMeal.setFat(meal.getFat());
        mealRepository.save(oldMeal);
        return "success"; // success
    }

    public String deleteMeal(Integer adminId,Integer mealId){
        Admin admin=adminRepository.findAdminById(adminId);
        if (admin==null){
            return "The admin id is not exits";
        }
       Meal meal =mealRepository.findMealById(mealId);
            if (meal==null){
                return "The meal id is not exits";
            }

        mealRepository.deleteById(mealId);
        return "success";
    }
//10
    public Meal calculateMealByGrams(Integer mealId, Double grams) {
        Meal meal = mealRepository.findMealById(mealId);
        if (meal == null) {
            return null;
        }
        if (grams == null || grams <= 0) {
            return null;
        }
        meal.setCalories(formatDouble(meal.getCalories() *grams));
        meal.setProtein(formatDouble(meal.getProtein()* grams));
        meal.setCarb(formatDouble(meal.getCarb() *grams));
        meal.setFat(formatDouble(meal.getFat()* grams));
        return meal;
    }
//helpper
    public double formatDouble(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
//11
    public List<Meal> mealsByCategory(Integer categoryId){

        return mealRepository.findAllByCategoryId(categoryId);
    }



}
