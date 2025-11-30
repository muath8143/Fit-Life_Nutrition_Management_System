package com.example.caostone2.Controller;

import com.example.caostone2.Api.ApiResponse;
import com.example.caostone2.Model.UserMealPlan;
import com.example.caostone2.Service.UserMealPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user-meal-plan")
@RequiredArgsConstructor
public class UserMealPlanController {

    private final UserMealPlanService userMealPlanService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUserPlans() {
        List<UserMealPlan> plans = userMealPlanService.allUserMealsPlans();
        if (plans.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No plans in system"));
        }
        return ResponseEntity.status(200).body(plans);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUserMealPlan(@RequestBody @Valid UserMealPlan userMealPlan, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        String response = userMealPlanService.addMealPlan(userMealPlan);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("User meal plan added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @PutMapping("/update/{planId}")
    public ResponseEntity<?> updateUserMealPlan(@PathVariable Integer planId, @RequestBody @Valid UserMealPlan userMealPlan, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        String response = userMealPlanService.updateUserMealPlan(planId, userMealPlan);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("User meal plan updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{userId}/{planId}")
    public ResponseEntity<?> deleteUserPlan(@PathVariable Integer userId, @PathVariable Integer planId) {

        String response = userMealPlanService.deleteUserPlan(userId, planId);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("User meal plan deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
//3
    @PostMapping("/generate/{userId}/{date}")
    public ResponseEntity<?> generateDailyPlan(@PathVariable Integer userId, @PathVariable LocalDate date) {
        String response = userMealPlanService.generateDailyPlan(userId, date);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("Daily meal plan generated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
//4
    @GetMapping("/daily-plan/{userId}/{date}")
    public ResponseEntity<?> getDailyPlan(@PathVariable Integer userId, @PathVariable LocalDate date) {
        List<UserMealPlan> plans = userMealPlanService.getDailyPlan(userId, date);
        if (plans == null) {
            return ResponseEntity.status(400).body(new ApiResponse("The user id is not exits"));
        }
        if (plans.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No plans for this day"));
        }
        return ResponseEntity.status(200).body(plans);
    }
//5
    @GetMapping("/daily-summary/{userId}/{date}")
    public ResponseEntity<?> getDailySummary(@PathVariable Integer userId, @PathVariable LocalDate date) {
        String response = userMealPlanService.getDailySummary(userId, date);
        if (response.contains("success")){
            return ResponseEntity.status(200).body(new ApiResponse(response));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
//6
    @PutMapping("/mark-meal-eaten/{userId}/{planId}")
    public ResponseEntity<?> markMealAsEaten(@PathVariable Integer userId, @PathVariable Integer planId){
        String response = userMealPlanService.markMealAsEaten(userId, planId);
        if (response.equals("success")){
            return ResponseEntity.status(200).body(new ApiResponse("The meal plan mark successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
//7
    @PutMapping("/replace/{userId}/{planId}/{mealId}/{grams}")
    public ResponseEntity<?> replaceMeal(@PathVariable Integer userId, @PathVariable Integer planId, @PathVariable Integer mealId, @PathVariable Double grams) {
        String response = userMealPlanService.replaceMeal(userId, planId, mealId, grams);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("The user meal plan updated with new meal successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
//8
    @PostMapping("/copy/{userId}/{fromDate}/{toDate}")
    public ResponseEntity<?> copyDailyPlan(@PathVariable Integer userId, @PathVariable LocalDate fromDate, @PathVariable LocalDate toDate) {
        String response = userMealPlanService.copyPlan(userId, fromDate, toDate);

        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("Daily meal plan copied successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
//9
    @PutMapping("/update-grams/{userId}/{planId}/{grams}")
    public ResponseEntity<?> updateGrams(@PathVariable Integer userId, @PathVariable Integer planId, @PathVariable Double grams) {
        String response = userMealPlanService.updateGrams(userId, planId, grams);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("The grams of meal updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
//12
    @DeleteMapping("/delete-plan-day/{userId}/{day}")
    public ResponseEntity<?> deletePlanDay(@PathVariable Integer userId,@PathVariable LocalDate day){
        String response=userMealPlanService.deletePlanByDate(userId, day);
        if (response.equals("success")){
            return ResponseEntity.status(200).body(new ApiResponse("The plan of day: "+day+" is deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
//14
    @GetMapping("/meals-user-by-category/{userId}/{category}")
    public ResponseEntity<?> AllMealsByUserIdAndCategoryName(@PathVariable Integer userId,@PathVariable String category){
        List<UserMealPlan> allMealsByCategory=userMealPlanService.findAllMealsForUserByCategoryName(userId, category);
        if (allMealsByCategory==null){
            return ResponseEntity.status(400).body(new ApiResponse("The user id is not exits"));
        }
        if (allMealsByCategory.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No meals in this category: "+category));
        }
        return ResponseEntity.status(200).body(allMealsByCategory);
    }
//15
    @PutMapping("/mark-plan-not-eaten/{userId}/{planId}")
    public ResponseEntity<?> markNotEaten(@PathVariable Integer userId,@PathVariable Integer planId){
        String response = userMealPlanService.markMealAsNotEaten(userId, planId);
        if (response.equals("success")){
            return ResponseEntity.status(200).body(new ApiResponse("The meal plan mark not eaten successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
}