package com.ddukeong.dkmatching.repository;

import com.ddukeong.dkmatching.model.One;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneRepository extends JpaRepository<One, Long> {
}
