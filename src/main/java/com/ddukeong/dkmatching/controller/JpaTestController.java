package com.ddukeong.dkmatching.controller;

import com.ddukeong.dkmatching.model.TestAccount;
import com.ddukeong.dkmatching.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JpaTestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/jpa/testAll")
    @ResponseBody
    public String testAll() {

        TestAccount testAccount = new TestAccount();
        testAccount.setId("testId1");
        testAccount.setName("testName1");

        TestAccount testAccount2 = new TestAccount();
        testAccount.setId("testId2");
        testAccount.setName("testName2");

        testAccount = testService.save(testAccount);
        testAccount2 = testService.save(testAccount2);

        testService.findAll().forEach(account -> System.out.println(account.getId()));

        testAccount.setName("updatedName1");
        TestAccount sameWithTestAccount = testService.findBySeq(testAccount.getTestAccountSeq());

        System.out.println("=======================");
        System.out.println(sameWithTestAccount);
        System.out.println(sameWithTestAccount.getName());
        //TODO save안해도 select 했을땐 동일값 나오는것처럼 보임 실제 db에선 어떤지 확인 필요 / 자체 layer에 캐시가 있는걸까
        System.out.println(testAccount);
        System.out.println(testAccount.getName());
        System.out.println("=======================");

        testService.updateV1(testAccount.getTestAccountSeq(), testAccount);
        testAccount.setName("updatedName2");
        testService.updateV2(testAccount.getTestAccountSeq(), testAccount);

        testService.deleteBySeq(testAccount.getTestAccountSeq());
        testService.deleteBySeq(testAccount2.getTestAccountSeq());
        return "Done";
    }

}
