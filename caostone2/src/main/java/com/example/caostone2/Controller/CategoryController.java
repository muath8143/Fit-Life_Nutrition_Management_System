package com.example.caostone2.Controller;

import com.example.caostone2.Api.ApiResponse;
import com.example.caostone2.Model.Category;
import com.example.caostone2.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No categories in system"));
        }
        return ResponseEntity.status(200).body(categories);
    }

    @PostMapping("/add/{adminId}")
    public ResponseEntity<?> addCategory(@PathVariable Integer adminId, @RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        String response = categoryService.addCategory(adminId, category);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("Category added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @PutMapping("/update/{adminId}/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer adminId, @PathVariable Integer categoryId, @RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        String response = categoryService.updateCategory(adminId, categoryId, category);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("Category updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{adminId}/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer adminId, @PathVariable Integer categoryId) {
        String response = categoryService.deleteCategory(adminId, categoryId);

        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
}