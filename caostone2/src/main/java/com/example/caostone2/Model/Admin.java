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
public class Admin {

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

    @NotEmpty(message = "The password is required")
    @Column(columnDefinition = "varchar(30) not null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$",message = "Password must contain lowercase, uppercase, number, and symbol")
    private String password;

    @NotNull(message ="The age is required")
    @Positive(message= "The age must be positive number")
    @Column(columnDefinition = "int not null")
    private Integer age;

    @NotEmpty(message= "The gender is required")
    @Pattern(regexp="male|female",message = "The gender must be male or female")
    @Column(columnDefinition="varchar(6) not null")
    private String gender;

}
