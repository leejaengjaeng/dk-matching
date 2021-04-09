package com.ddukeong.dkmatching.repository.jpaSample;

import com.ddukeong.dkmatching.model.jpaSample.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
