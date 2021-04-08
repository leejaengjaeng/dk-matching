package com.ddukeong.dkmatching.service;

import com.ddukeong.dkmatching.model.TestAccount;
import com.ddukeong.dkmatching.repository.TestAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CrudTestService {
    private final TestAccountRepository testAccountRepository;

    public CrudTestService(TestAccountRepository testAccountRepository) {
        this.testAccountRepository = testAccountRepository;
    }

    public TestAccount save(TestAccount testAccount) {
        testAccountRepository.save(testAccount);
        return testAccount;
    }

    public TestAccount updateV1(Long seq, TestAccount toUpdate) {
        log.info("updateV1 Id만 바꾸고 새로 save - update될 모델 {}", toUpdate);
        toUpdate.setTestAccountSeq(seq);
        return save(toUpdate);
    }

    public void updateV2(Long seq, TestAccount toUpdate) {
        log.info("updateV2 deep copy 해봄 - update될 모델 {}", toUpdate);
        TestAccount testAccount = findBySeq(seq);

        if (testAccount != null) {
            //Deep copy all values except seq(pk)
            testAccount.setId(toUpdate.getId());
            testAccount.setName(toUpdate.getName());
        }
    }

    public TestAccount updateV3(Long seq, TestAccount toUpdate) {
        log.info("updateV3 v2+return 해봄 - update될 모델 {}", toUpdate);
        TestAccount testAccount = findBySeq(seq);

        if (testAccount != null) {
            //Deep copy all values except seq(pk)
            testAccount.setId(toUpdate.getId());
            testAccount.setName(toUpdate.getName());
        }
        return testAccount;
    }

    public void deleteAll() {
        testAccountRepository.deleteAll();
    }

    public void deleteBySeq(Long testAccountSeq) {
        testAccountRepository.deleteById(testAccountSeq);
    }

    public TestAccount findBySeq(Long testAccountSeq) {
        return testAccountRepository.findById(testAccountSeq).orElse(null);
    }

    public List<TestAccount> findAll() {
        return testAccountRepository.findAll();
    }
}
