package com.example.caostone2.Repository;

import com.example.caostone2.Model.UserMealPlan;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserMealPlanRepository extends JpaRepository<UserMealPlan,Integer> {
    UserMealPlan findUserMealPlanById(Integer id);

    List<UserMealPlan> findAllByUserIdAndDate(Integer userId, LocalDate date);

    @Modifying
    @Transactional
    @Query("delete from UserMealPlan u where u.userId = ?1 and u.date = ?2")
    void deleteAllByUserIdAndDate(Integer userId, LocalDate date);

    @Query("select u from UserMealPlan u where u.userId=?1 and u.mealTime=?2")
    List<UserMealPlan> userMealPlanByCategoryName(Integer userId ,String category);
}
