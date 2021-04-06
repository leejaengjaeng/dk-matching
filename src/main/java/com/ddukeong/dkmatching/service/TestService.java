package com.ddukeong.dkmatching.service;

import com.ddukeong.dkmatching.model.TestAccount;
import com.ddukeong.dkmatching.repository.TestAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TestService {
    private final TestAccountRepository testAccountRepository;

    public TestService(TestAccountRepository testAccountRepository) {
        this.testAccountRepository = testAccountRepository;
    }

    public TestAccount save(TestAccount testAccount) {
        log.info("save 전후 동일 객체여부 확인 - save 전, id 확인 : {}, {}", testAccount, testAccount.getTestAccountSeq());
        testAccountRepository.save(testAccount);
        log.info("save 전후 동일 객체여부 확인 - save 후, id 확인 : {}, {}", testAccount, testAccount.getTestAccountSeq());
        return testAccount;
    }

    public void updateV1(Long seq, TestAccount toUpdate) {
        log.info("updateV1 Id만 바꿔서 해봄 - update될 모델 {}", toUpdate);
        toUpdate.setTestAccountSeq(seq);
        save(toUpdate);


        TestAccount afterAccount = findBySeq(seq);
        log.info("updateV1 Id만 바꿔서 해봄 - update후 다시 조회한 모델 {}", afterAccount);
    }

    public void updateV2(Long seq, TestAccount toUpdate) {
        log.info("updateV2 deep copy 해봄 - update될 모델 {}", toUpdate);
        TestAccount testAccount = findBySeq(seq);

        if(testAccount!=null) {
            testAccount.setId(toUpdate.getId());
            testAccount.setName(toUpdate.getName());
            save(testAccount);

            TestAccount afterAccount = findBySeq(seq);
            log.info("updateV2 deep copy 해봄 - update후 다시 조회한 모델 {}", afterAccount);
        }

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
