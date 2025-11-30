package com.example.caostone2.Service;

import com.example.caostone2.Model.Admin;
import com.example.caostone2.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public List<Admin> getAllAdmins(Integer adminId){
        Admin admin =adminRepository.findAdminById(adminId);
        if (admin==null){
            return null; //not found admin id
        }
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public String updateAdmin(Integer adminId,Admin admin){
        Admin oldAdmin=adminRepository.findAdminById(adminId);
        if (oldAdmin==null){
            return "the admin id is not exits";
        }
        oldAdmin.setName(admin.getName());
        oldAdmin.setEmail(admin.getEmail());
        oldAdmin.setPassword(admin.getPassword());
        oldAdmin.setGender(admin.getGender());
        oldAdmin.setAge(admin.getAge());
        adminRepository.save(oldAdmin);
        return "success";
    }

    public String deleteAdmin(Integer adminId){
        Admin admin=adminRepository.findAdminById(adminId);
        if (admin==null){
            return "The admin id is not exits";
        }
        adminRepository.deleteById(adminId);
        return "success";
    }
}
