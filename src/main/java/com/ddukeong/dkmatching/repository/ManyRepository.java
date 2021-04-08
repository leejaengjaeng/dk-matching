package com.ddukeong.dkmatching.repository;

import com.ddukeong.dkmatching.model.Many;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManyRepository extends JpaRepository<Many, Long> {
}
