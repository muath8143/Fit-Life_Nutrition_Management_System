package com.example.caostone2.Controller;

import com.example.caostone2.Api.ApiResponse;
import com.example.caostone2.Model.User;
import com.example.caostone2.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No users in system"));
        }
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("The User added successfully"));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        String response = userService.updateUser(userId, user);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {

        String response = userService.deleteUser(userId);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("The User deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
//1
    @PutMapping("/update-target-calories/{userId}/{calories}")
    public ResponseEntity<?> updateTargetCalories(@PathVariable Integer userId, @PathVariable Double calories) {

        String response = userService.updateTargetCalories(userId, calories);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("Target calories updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
//2
    @PutMapping("/calculate-target-calories/{userId}")
    public ResponseEntity<?> calculateTargetCalories(@PathVariable Integer userId) {
        Double targetCalories = userService.calculateTargetCalories(userId);

        if (targetCalories == null) {
            return ResponseEntity.status(400).body(new ApiResponse("The user id is not exits"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Target calories calculated: " + targetCalories));
    }
}