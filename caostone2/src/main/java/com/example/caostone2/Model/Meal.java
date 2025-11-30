package com.example.caostone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The name of meal is required")
    @Size(min = 5,max = 30,message = "The name of meal must be at least 5 characters and no greater than 30")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotNull(message = "The category id is required")
    @Column(columnDefinition = "int not null")
    private Integer categoryId;

    @NotEmpty(message = "The description is required")
    @Size(min = 15,max = 50,message = "The description must be has at least 15 characters and no greater than 50")
    @Column(columnDefinition = "varchar(50) not null")
    private String description;

    @NotNull(message = "The calories is required")
    @Positive(message = "The calories must be positive")
    @Column(columnDefinition = "float not null")
    private Double calories;

    @NotNull(message = "The protein gram is required")
    @Positive(message = "The gram of protein must be positive")
    @Column(columnDefinition = "float not null")
    private Double protein;

    @NotNull(message = "The carb gram is required")
    @Positive(message = "The gram of carb must be positive")
    @Column(columnDefinition = "float not null")
    private Double carb;

    @NotNull(message = "The fat gram is required")
    @Positive(message = "The gram of fat must be positive")
    @Column(columnDefinition = "float not null")
    private Double fat;
}
