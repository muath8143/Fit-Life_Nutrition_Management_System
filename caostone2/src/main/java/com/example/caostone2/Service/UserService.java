package com.example.caostone2.Service;

import com.example.caostone2.Model.User;
import com.example.caostone2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        user.setTargetCalories(0.0);
        userRepository.save(user);
    }

    public String updateUser(Integer userId, User user){
        User oldUser = userRepository.findUserById(userId);
        if (oldUser == null){
            return "The user id is not exits";
        }
        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        oldUser.setPassword(user.getPassword());
        oldUser.setAge(user.getAge());
        oldUser.setWeight(user.getWeight());
        oldUser.setHeight(user.getHeight());
        oldUser.setGender(user.getGender());
        oldUser.setActivityLevel(user.getActivityLevel());
        oldUser.setGoal(user.getGoal());
        userRepository.save(oldUser);
        return "success";
    }

    public String deleteUser(Integer userId){
        User user =userRepository.findUserById(userId);
        if (user==null){
            return "The user id is not exits";
        }
        userRepository.deleteById(userId);
        return "success";
    }
//1
    public String updateTargetCalories(Integer userId, Double calories){
        User user = userRepository.findUserById(userId);
        if (user == null){
            return "The user id is not exits";
        }
        if (calories>8000 || calories<500){
            return "The target calories must be between 500 and 8000";
        }
        calories= Math.round(calories*10.0)/10.0;
        user.setTargetCalories(calories);
        userRepository.save(user);
        return "success";
    }
//2
    public Double calculateTargetCalories(Integer userId){
        User user = userRepository.findUserById(userId);
        if (user == null){
            return null;
        }
        double bmr;
        if (user.getGender().equals("male")) {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
        } else {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161; //female
        }
        double targetCalories = getMaintenance(bmr,user.getActivityLevel(), user.getGoal());
        targetCalories= (double) Math.round(targetCalories * 10.0) /10;
        user.setTargetCalories(targetCalories);
        userRepository.save(user);
        return targetCalories;
    }
//helper
    public Double getMaintenance(Double bmr,String activity  ,String goal) {
        double activityFactor;
        switch (activity) {
            case "sedentary":
                activityFactor = 1.2;
                break;
            case "light":
                activityFactor = 1.375;
                break;
            case "moderate":
                activityFactor = 1.55;
                break;
            case "active":
                activityFactor = 1.725;
                break;
            case "athlete":
                activityFactor = 1.9;
                break;
            default:
                activityFactor = 1.25;
        }
        double maintenance = bmr * activityFactor;
        double targetCalories;
        switch (goal) {
            case "lose_weight":
                targetCalories = maintenance * 0.8;
                break;
            case "gain_weight":
                targetCalories = maintenance * 1.15;
                break;
            default:
                targetCalories = maintenance;
                break;
        }
        return targetCalories;
    }
}
