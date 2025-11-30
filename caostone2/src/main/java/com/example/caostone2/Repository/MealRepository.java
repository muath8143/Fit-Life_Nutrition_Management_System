package com.example.caostone2.Repository;

import com.example.caostone2.Model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal,Integer> {
    Meal findMealById(Integer id);

    @Query(value = "select * from meal where category_id = ?1 order by rand() limit 1", nativeQuery = true)
    Meal findRandomMealByCategory(Integer categoryId);

    List<Meal> findAllByCategoryId(Integer categoryId);
}
