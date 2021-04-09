package com.ddukeong.dkmatching.repository.jpaSample;

import com.ddukeong.dkmatching.model.jpaSample.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
