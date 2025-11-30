package com.example.caostone2.Controller;

import com.example.caostone2.Api.ApiResponse;
import com.example.caostone2.Model.Admin;
import com.example.caostone2.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/get-all/{adminId}")
    public ResponseEntity<?> getAllAdmins(@PathVariable Integer adminId) {
        List<Admin> admins = adminService.getAllAdmins(adminId);
        if (admins == null) {
            return ResponseEntity.status(400).body(new ApiResponse("The admin id is not exists"));
        }
        return ResponseEntity.status(200).body(admins);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody @Valid Admin admin, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin added successfully"));
    }

    @PutMapping("/update/{adminId}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer adminId, @RequestBody @Valid Admin admin, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        String response = adminService.updateAdmin(adminId, admin);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("Admin updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{adminId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer adminId) {
        String response = adminService.deleteAdmin(adminId);
        if (response.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("Admin deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(response));
    }
}