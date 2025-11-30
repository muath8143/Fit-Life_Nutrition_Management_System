package com.example.caostone2.Service;

import com.example.caostone2.Model.Admin;
import com.example.caostone2.Model.Category;
import com.example.caostone2.Repository.AdminRepository;
import com.example.caostone2.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public String addCategory(Integer adminId , Category category){
        Admin admin =adminRepository.findAdminById(adminId);
        if (admin==null){
            return "The admin id is not exits";
        }
        categoryRepository.save(category);
        return "success";
    }

    public String updateCategory (Integer adminId ,Integer categoryId,Category category){
        Admin admin=adminRepository.findAdminById(adminId);
        if (admin==null){
            return "The admin id is not exits";
        }
        Category oldCategory=categoryRepository.findCategoryById(categoryId);
        if (oldCategory==null){
            return "The category id is not exits";
        }
        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
        return "success";
    }

    public String deleteCategory(Integer adminId,Integer categoryId){
        Admin admin=adminRepository.findAdminById(adminId);
        if (admin==null){
            return "The admin id is not exits";
        }
        Category oldCategory=categoryRepository.findCategoryById(categoryId);
        if (oldCategory==null){
            return "The category id is not exits";
        }
        categoryRepository.deleteById(categoryId);
        return "success";
    }
}
