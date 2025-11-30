package com.example.caostone2.Controller;

import com.example.caostone2.Api.ApiResponse;
import com.example.caostone2.Model.Meal;
import com.example.caostone2.Service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllMeals() {
        List<Meal> meals =mealService.getAllMeals();
        if (meals.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No meals in system"));
        }
        return ResponseEntity.status(200).body(meals);
    }

    @PostMapping("/add/{adminId}")
    public ResponseEntity<?> addMeal(@PathVariable Integer adminId, @RequestBody @Valid Meal meal, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        String response= mealService.addMeal(adminId, meal);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("Meal added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @PutMapping("/update/{adminId}/{mealId}")
    public ResponseEntity<?> updateMeal(@PathVariable Integer adminId, @PathVariable Integer mealId, @RequestBody @Valid Meal meal, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        String response = mealService.updateMeal(adminId, mealId, meal);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("The meal updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{adminId}/{mealId}")
    public ResponseEntity<?> deleteMeal(@PathVariable Integer adminId, @PathVariable Integer mealId) {
        String response= mealService.deleteMeal(adminId, mealId);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("The meal deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
//10
    @GetMapping("/meal-info/{mealId}/{grams}")
    public ResponseEntity<?> getMealInfo(@PathVariable Integer mealId, @PathVariable Double grams) {
        Meal mealInfo =mealService.calculateMealByGrams(mealId, grams);
        if (mealInfo== null) {
            return ResponseEntity.status(400).body(new ApiResponse("Invalid meal id or grams"));
        }
        return ResponseEntity.status(200).body(mealInfo);
    }
//11
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<?> getMealsByCategory(@PathVariable Integer categoryId) {
        List<Meal> meals= mealService.mealsByCategory(categoryId);
        if (meals.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No meals found for this category"));
        }

        return ResponseEntity.status(200).body(meals);
    }
}