package com.example.caostone2.Service;

import com.example.caostone2.Model.Meal;
import com.example.caostone2.Model.User;
import com.example.caostone2.Model.UserMealPlan;
import com.example.caostone2.Repository.MealRepository;
import com.example.caostone2.Repository.UserMealPlanRepository;
import com.example.caostone2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMealPlanService {
    private final UserMealPlanRepository userMealPlanRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final WhatsAppService whatsAppService;

    public List<UserMealPlan> allUserMealsPlans(){
        return userMealPlanRepository.findAll();
    }

    public String addMealPlan(UserMealPlan userMealPlan){
        User user = userRepository.findUserById(userMealPlan.getUserId());
        if (user == null){
            return "The user id is not exits";
        }

        Meal meal = mealRepository.findMealById(userMealPlan.getMealId());
        if (meal == null){
            return "The meal id is not exits";
        }

        userMealPlan.setEaten(false);
        userMealPlanRepository.save(userMealPlan);
        return "success";
    }

    public String updateUserMealPlan(Integer planId,UserMealPlan userMealPlan){
        UserMealPlan oldUserPlan= userMealPlanRepository.findUserMealPlanById(planId);
        if (oldUserPlan==null){
            return "The user meal plan is not exits";
        }
        if (!oldUserPlan.getUserId().equals(userMealPlan.getUserId())){
            return "You cannot update this user plan";
        }
        Meal meal=mealRepository.findMealById(userMealPlan.getMealId());
        if (meal==null){
            return "The meal id is not exits";
        }
        oldUserPlan.setMealId(userMealPlan.getMealId());
        oldUserPlan.setGram(userMealPlan.getGram());
        oldUserPlan.setDate(userMealPlan.getDate());
        oldUserPlan.setMealTime(userMealPlan.getMealTime());
        userMealPlanRepository.save(oldUserPlan);
        return "success";
    }

    public String deleteUserPlan(Integer userId,Integer userPlanId){
        User user=userRepository.findUserById(userId);
        if (user==null){
            return "The user id is not exits";
        }
        UserMealPlan userMealPlan=userMealPlanRepository.findUserMealPlanById(userPlanId);
        if (userMealPlan==null){
            return "The user meal plan is not exits";
        }
        if (!userId.equals(userMealPlan.getUserId())){
            return "You cannot delete this user plan";
        }
        userMealPlanRepository.deleteById(userPlanId);
        return "success";
    }
//3
    public String generateDailyPlan(Integer userId, LocalDate date) {
        User user = userRepository.findUserById(userId);
        if (user == null){
            return "The user id is not exits";
        }
        if (user.getTargetCalories() <=0){
            return "The user target calories is not set";
        }
        List<UserMealPlan> existingPlans = userMealPlanRepository.findAllByUserIdAndDate(userId, date);
        if (!existingPlans.isEmpty()){
            return "The user already has a meal plan for this date";
        }
        if (date.isBefore(LocalDate.now())){
            return "The date must be in present or future";
        }
        double breakfastTarget = user.getTargetCalories() *0.25; // 25%
        double lunchTarget= user.getTargetCalories() * 0.35; // 35%
        double dinnerTarget= user.getTargetCalories() * 0.30; // 30%
        double snackTarget =user.getTargetCalories() *0.10; // 10%

        Meal breakfastMeal = mealRepository.findRandomMealByCategory(1);
        if (breakfastMeal == null){
            return "There are no breakfast meals in the system";
        }
        createPlan(userId, date, "breakfast", breakfastTarget, breakfastMeal);

        Meal lunchMeal = mealRepository.findRandomMealByCategory(2);
        if (lunchMeal == null){
            return "There are no lunch meals in the system";
        }
        createPlan(userId, date, "lunch", lunchTarget, lunchMeal);

        Meal dinnerMeal = mealRepository.findRandomMealByCategory(3);
        if (dinnerMeal == null){
            return "There are no dinner meals in the system";
        }
        createPlan(userId, date, "dinner", dinnerTarget, dinnerMeal);

        Meal snackMeal = mealRepository.findRandomMealByCategory(4);
        if (snackMeal == null){
            return "There are no snack meals in the system";
        }
        createPlan(userId, date, "snack", snackTarget, snackMeal);
        return "success";
    }
//helper
    private void createPlan(Integer userId, LocalDate date, String mealTime, double targetCaloriesMeal, Meal meal) {
        Double caloriesPerGram = meal.getCalories();
        if (caloriesPerGram <= 0){
            return;
        }
        double grams = targetCaloriesMeal / caloriesPerGram;
        grams=Math.round(grams*10.0)/10.0;
        UserMealPlan plan = new UserMealPlan();
        plan.setUserId(userId);
        plan.setMealId(meal.getId());
        plan.setGram(grams);
        plan.setDate(date);
        plan.setMealTime(mealTime);
        plan.setEaten(false);
        userMealPlanRepository.save(plan);
    }
//4
    public List<UserMealPlan> getDailyPlan(Integer userId, LocalDate date) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return null; // user id is not exits
        }
        return userMealPlanRepository.findAllByUserIdAndDate(userId, date);
    }
//5
    public String getDailySummary(Integer userId, LocalDate date) {
        User user = userRepository.findUserById(userId);
        if (user == null){
            return "The user id is not exits";
        }

        Double targetCalories = user.getTargetCalories();
        if (targetCalories <= 0){
           return "Your target calories is 0";
        }

        List<UserMealPlan> plans = userMealPlanRepository.findAllByUserIdAndDate(userId, date);
        if (plans.isEmpty()){
            return "You don't have any plan for this day";
        }
        double totalCalories=0;
        double totalProtein=0;
        double totalCarb= 0;
        double totalFat=0;

        for (UserMealPlan plan : plans){
            if (plan.getEaten()){
                Meal meal = mealRepository.findMealById(plan.getMealId());
                double grams= plan.getGram();
                totalCalories +=grams * meal.getCalories();
                totalProtein += grams* meal.getProtein();
                totalCarb+= grams * meal.getCarb();
                totalFat+= grams *meal.getFat();
            }

        }
        double remainingCalories =targetCalories -totalCalories;
        return String.format("success,,Date: %s, Target calories: %.1f, Eaten calories: %.1f, Remaining calories: %.1f, Total protein: %.1f, Total carb: %.1f, Total fat: %.1f",
                date, targetCalories, totalCalories, remainingCalories, totalProtein, totalCarb, totalFat
        );
    }
//6
    public String markMealAsEaten(Integer userId, Integer planId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return "The user id is not exits";
        }
        UserMealPlan plan = userMealPlanRepository.findUserMealPlanById(planId);
        if (plan == null) {
            return "The user meal plan is not exits";
        }
        if (!plan.getUserId().equals(userId)) {
            return "You cannot update this user plan";
        }
        if (plan.getEaten().equals(true)) {
            return "This meal is already eaten";
        }
        plan.setEaten(true);
        userMealPlanRepository.save(plan);
        whatsAppService.sendWhatsAppMessage(user.getPhoneNumber(),getDailySummary(userId,plan.getDate()));
        return "success";
    }
//7
    public String replaceMeal(Integer userId, Integer planId, Integer newMealId, Double grams) {
        User user = userRepository.findUserById(userId);
        if (user == null){
            return "The user id is not exits";
        }
        UserMealPlan plan = userMealPlanRepository.findUserMealPlanById(planId);
        if (plan == null) {
            return "The user meal plan is not exits";
        }
        if (!plan.getUserId().equals(userId)) {
            return "You cannot update this user plan";
        }
        Meal newMeal = mealRepository.findMealById(newMealId);
        if (newMeal == null) {
            return "The meal id is not exits";
        }
        if (grams == null || grams <= 0) {
            return "The grams of meal must be positive";
        }
        grams = Math.round(grams* 10.0)/10.0;
        plan.setMealId(newMealId);
        plan.setGram(grams);
        plan.setEaten(false);
        userMealPlanRepository.save(plan);
        return "success";
    }

//8
    public String copyPlan(Integer userId, LocalDate fromDate, LocalDate toDate) {
        User user = userRepository.findUserById(userId);
        if (user == null){
            return "The user id is not exits";
        }
        List<UserMealPlan> oldPlans = userMealPlanRepository.findAllByUserIdAndDate(userId, fromDate);
        if (oldPlans.isEmpty()){
            return "You don't have any plan for this day to copy";
        }
        List<UserMealPlan> newPlans = userMealPlanRepository.findAllByUserIdAndDate(userId, toDate);
        if (!newPlans.isEmpty()){
            return "The user already has a meal plan for this date";
        }
        for (UserMealPlan oldPlan : oldPlans){
            UserMealPlan newPlan = new UserMealPlan();
            newPlan.setUserId(userId);
            newPlan.setMealId(oldPlan.getMealId());
            newPlan.setGram(oldPlan.getGram());
            newPlan.setDate(toDate);
            newPlan.setMealTime(oldPlan.getMealTime());
            newPlan.setEaten(false);
            userMealPlanRepository.save(newPlan);
        }
        return "success";
    }
//9
    public String updateGrams(Integer userId, Integer planId, Double grams) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return "The user id is not exits";
        }
        UserMealPlan plan = userMealPlanRepository.findUserMealPlanById(planId);
        if (plan == null) {
            return "The user meal plan is not exits";
        }
        if (!plan.getUserId().equals(userId)) {
            return "You cannot update this user plan";
        }
        if (grams==null||grams<=0) {
            return "The grams of meal must be positive";
        }
        grams = Math.round(grams * 10.0) / 10.0;
        plan.setGram(grams);
        userMealPlanRepository.save(plan);
        return "success";
    }
//12
    public String deletePlanByDate(Integer userId, LocalDate date) {
        User user = userRepository.findUserById(userId);
        if (user == null){
            return "The user id is not exits";
        }
        List<UserMealPlan> plans = userMealPlanRepository.findAllByUserIdAndDate(userId, date);
        if (plans.isEmpty()){
            return "No plans found for this day";
        }
        userMealPlanRepository.deleteAllByUserIdAndDate(userId, date);
        return "success";
    }
//14
    public List<UserMealPlan> findAllMealsForUserByCategoryName(Integer userId,String category){
        User user=userRepository.findUserById(userId);
        if (user==null){
            return null;
        }
        return userMealPlanRepository.userMealPlanByCategoryName(userId, category);
    }
//15
    public String markMealAsNotEaten(Integer userId,Integer planId){
        User user=userRepository.findUserById(userId);
        if (user==null){
            return "The user id is not exits";
        }
        UserMealPlan userMealPlan=userMealPlanRepository.findUserMealPlanById(planId);
        if (userMealPlan==null){
            return "The plan id is not exits";
        }
        if (!userId.equals(userMealPlan.getUserId())){
            return "You cannot update this user plan";
        }
        if (userMealPlan.getEaten().equals(false)){
            return "The meal is already not eaten";
        }
        userMealPlan.setEaten(false);
        userMealPlanRepository.save(userMealPlan);
        return "success";
    }
}
