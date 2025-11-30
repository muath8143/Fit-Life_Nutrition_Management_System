package com.example.caostone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message ="The category name must be one of them breakfast lunch or dinner or snack")
    @Pattern(regexp = "breakfast|lunch|dinner|snack",message = "The name of category must be breakfast lunch or dinner or snack")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String name;

}
