package com.example.caostone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The name is required")
    @Size(min = 5, max = 30,message = "The name must be has at least 5 characters and no greater than 30")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotEmpty(message = "The email is required")
    @Email(message = "The email must be valid format email")
    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;

    @NotEmpty(message = "The phone number is required")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with 05 and be 10 digits")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;

    @NotEmpty(message = "The password is required")
    @Size(max = 30,message = "The password must be less than 30 characters")
    @Column(columnDefinition = "varchar(30) not null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$",message = "Password must contain lowercase, uppercase, number, and symbol")
    private String password;

    @NotNull(message ="The age is required")
    @Positive(message= "The age must be positive number")
    @Column(columnDefinition = "int not null")
    private Integer age;

    @NotNull(message = "The weight is required")
    @Positive(message= "The weight must be positive number")
    @Column(columnDefinition ="float not null")
    private Double weight;

    @NotNull(message ="The height is required")
    @Positive(message= "The height must be positive number")
    @Column(columnDefinition ="float not null")
    private Double height;

    @NotEmpty(message= "The gender is required")
    @Pattern(regexp="male|female",message = "The gender must be male or female")
    @Column(columnDefinition="varchar(8) not null")
    private String gender;

    @NotEmpty(message = "The activity level is required")
    @Pattern(regexp = "sedentary|light|moderate|active|athlete",message = "The activityLevel must be one of them: sedentary or light or moderate or active or athlete")
    @Column(columnDefinition = "varchar(10) not null")
    private String activityLevel;

    @NotEmpty(message= "The goal is required")
    @Pattern(regexp ="lose_weight|maintain|gain_weight")
    @Column(columnDefinition = "varchar(15) not null")
    private String goal;

    @Column(columnDefinition ="float") // set 0 in service
    private Double targetCalories;
}
