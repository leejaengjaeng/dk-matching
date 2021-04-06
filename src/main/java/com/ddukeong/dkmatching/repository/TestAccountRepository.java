package com.ddukeong.dkmatching.repository;

import com.ddukeong.dkmatching.model.TestAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestAccountRepository extends JpaRepository<TestAccount, Long> {
}
