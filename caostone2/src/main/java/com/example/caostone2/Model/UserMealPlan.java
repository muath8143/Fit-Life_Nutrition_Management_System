package com.example.caostone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserMealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "The user id is required")
    @Column(columnDefinition ="int not null")
    private Integer userId;

    @NotNull(message = "The meal id is required")
    @Column(columnDefinition ="int not null")
    private Integer mealId;


    @NotNull(message = "The date is required")
    @Column(columnDefinition = "date not null")
    private LocalDate date;

    @NotEmpty(message ="The meal time must be one of: breakfast, lunch, dinner, snack")
    @Pattern(regexp = "breakfast|lunch|dinner|snack",message ="The meal time must be one of: breakfast, lunch, dinner, snack")
    @Column(columnDefinition = "varchar(10) not null ")
    private String mealTime;

    @NotNull(message = "The grams of meal is required")
    @Positive(message = "The grams of meal must be positive")
    @Column(columnDefinition= "float not null")
    private Double gram;

    @Column(columnDefinition = "boolean ") // put false in service
    private Boolean eaten ;
}