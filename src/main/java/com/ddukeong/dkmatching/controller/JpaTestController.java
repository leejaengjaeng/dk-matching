package com.ddukeong.dkmatching.controller;

import com.ddukeong.dkmatching.model.Many;
import com.ddukeong.dkmatching.model.One;
import com.ddukeong.dkmatching.model.TestAccount;
import com.ddukeong.dkmatching.service.CrudTestService;
import com.ddukeong.dkmatching.service.RelationTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Controller
public class JpaTestController {

    private final CrudTestService crudTestService;
    private final RelationTestService relationTestService;


    /**
     * EntityManager는 thread 단위 shared 인것으로 추정됨
     * 디버그 찍어보면 entityManagerFactory의 프록시 객체(DI 때문)로 들어있는데
     * 여기서 clear하면 각 repository의 persistenceContext에 담긴 값도 날아감
     */
    private final EntityManager entityManager;

    public JpaTestController(CrudTestService crudTestService, RelationTestService relationTestService, EntityManager entityManager) {
        this.crudTestService = crudTestService;
        this.relationTestService = relationTestService;
        this.entityManager = entityManager;
    }

    /**
     * PersistenceContext 개념 잡기
     * https://www.youtube.com/watch?v=PMNSeD25Qko&feature=youtu.be
     * <p>
     * JpaRepository 인터페이스의 default 구현체로 SimpleJpaRepository 사용됨
     */
    @RequestMapping("/jpa/crud/testAll")
    @ResponseBody
    public String testAll() {

        // @formatter:off
        log.info("Save test");
        log.info("------------------------------------");
            TestAccount testAccount = new TestAccount("t1", "test1");
            TestAccount testAccount2 = new TestAccount("t2", "test2");
            testAccount = crudTestService.save(testAccount);
            testAccount2 = crudTestService.save(testAccount2);
            crudTestService.findAll().forEach(currentAccount -> log.info("{}",currentAccount));
        log.info("------------------------------------\n");


        log.info("Update not use test");
        log.info("------------------------------------");
            testAccount.setName("update not use");
            TestAccount sameWithTestAccount = crudTestService.findBySeq(testAccount.getTestAccountSeq());
            log.info("업데이트 수행안해도 업데이트 반영된건지 확인\n[{}]{}\n[{}]{}",
                    testAccount.hashCode(), testAccount,
                    sameWithTestAccount.hashCode(), sameWithTestAccount
            );
            log.info("Update 안했는데 바꼈음 -> PersistenceContext 때문");
        log.info("------------------------------------\n");

        log.info("Entity Manager test");
        log.info("------------------------------------");
            log.info("Current TestAccount : {}", testAccount);
            testAccount.setName("updateForEmTest");
            sameWithTestAccount = crudTestService.findBySeq(testAccount.getTestAccountSeq());
            log.info("After update sameWithTestAccount : {}", sameWithTestAccount);

            entityManager.clear();

            testAccount.setName("after Clear");
            sameWithTestAccount = crudTestService.findBySeq(testAccount.getTestAccountSeq());
            log.info("After clear sameWithTestAccount : {}", sameWithTestAccount);
            log.info("Entity Manager는 thread 단위로 shared 되는것으로 보임");
        log.info("------------------------------------\n");


        entityManager.clear();
        log.info("Update v1");
        log.info("------------------------------------");
            crudTestService.save(testAccount);

            log.info("Before update testAccount : {}", testAccount);
            testAccount2.setName("update v1");
            testAccount2 = crudTestService.updateV1(testAccount.getTestAccountSeq(), testAccount2);
            sameWithTestAccount = crudTestService.findBySeq(testAccount2.getTestAccountSeq());
            log.info("After update testAccount2 : {}", testAccount2);
            log.info("After update sameWithTestAccount : {}", sameWithTestAccount);

            testAccount2.setName("update v1 - check managed");
            sameWithTestAccount = crudTestService.findBySeq(testAccount2.getTestAccountSeq());
            log.info("Check managed - name will be 'update v1 - check managed' : {}", sameWithTestAccount);
            log.error("Managed 안됨...! V2가 안되는건 이해되는데 V1은 왜 안되지 ");
        log.info("------------------------------------\n");

        entityManager.clear();
        log.info("Update v2");
        log.info("------------------------------------");
            crudTestService.save(testAccount);

            log.info("Before update testAccount : {}", testAccount);
            testAccount2.setName("update v2");
            crudTestService.updateV2(testAccount.getTestAccountSeq(), testAccount2);
            sameWithTestAccount = crudTestService.findBySeq(testAccount2.getTestAccountSeq());
            log.info("After update testAccount2 : {}", testAccount2);
            log.info("After update sameWithTestAccount : {}", sameWithTestAccount);

            testAccount2.setName("update v2 - check managed");
            sameWithTestAccount = crudTestService.findBySeq(testAccount2.getTestAccountSeq());
            log.info("Check managed - name will be 'update v2 - check managed' : {}", sameWithTestAccount);
            log.error("이것도 Managed 안됨... 이건 당연한듯 testAccount2는 값을 전달했을뿐, 실제는 updateV2 내부에서 조회한 testAccount가 관리될테니");
        log.info("------------------------------------\n");

        entityManager.clear();
        log.info("Update v3");
        log.info("------------------------------------");
            crudTestService.save(testAccount);

            log.info("Before update testAccount : {}", testAccount);
            testAccount2.setName("update v3");
            testAccount2 = crudTestService.updateV3(testAccount.getTestAccountSeq(), testAccount2);
            sameWithTestAccount = crudTestService.findBySeq(testAccount2.getTestAccountSeq());
            log.info("After update testAccount2 : {}", testAccount2);
            log.info("After update sameWithTestAccount : {}", sameWithTestAccount);

            testAccount2.setName("update v3 - check managed");
            sameWithTestAccount = crudTestService.findBySeq(testAccount2.getTestAccountSeq());
            log.info("Check managed - name will be 'update v3 - check managed' : {}", sameWithTestAccount);
            log.warn("Managed 확인 완료.. 꼭 리턴을 받아야 하는건가..");
        log.info("------------------------------------\n");

        entityManager.clear();
        log.info("Collection Test");
        log.info("------------------------------------");
            Many many = new Many("m1","1번 Many");
            List<Many> allMany = relationTestService.getManys();
            log.info("DB에 생성됐는지 확인 -> 0개일듯 -> {}개", allMany.size());
            relationTestService.saveMany(many);
            log.info("다시 조회전 0개일듯 -> {}개", allMany.size());
            allMany = relationTestService.getManys();
            log.info("다시 조회후 1개일듯 -> {}개", allMany.size());
        log.info("------------------------------------\n");


        log.info("Delete test data");
            crudTestService.deleteAll();
            relationTestService.deleteAllMany();
        // @formatter:on

        return "Done";
    }

    @RequestMapping("/jpa/relation/test")
    @ResponseBody
    public String relationTest() {
        /**
         *  결론
         *  JPA 써서 설계할땐 단방향 연관관계로 만든 후 개발하다가 필요하면 양방향을 뚫기(코드만 변하고 DB는 그대로)
         *  일반적으로 양방향에서 관계의 주인은 FK가 들어갈 테이블이 주인
         *
         *  JPA를 처음 쓸때 관계의 주인쪽에 관계를 세팅하고 insert 하는게 어색해 보일수있는데 그렇게 해야지 참조가 됨
         *  ex)
         *  BookStore bookStore = new BookStore();
         *  Book book1 = new Book();
         *  Book book2 = new Book();
         *
         *  //이게 아니라
         *  save(book1);
         *  save(book2);
         *  bookStore.books.add(book1);
         *  bookStore.books.add(book2);
         *  save(bookStore);
         *  -> bookStore = getBookStore(id);
         *  -> bookStore.add(book3); //이게 DB에 바로 반영되는걸 막기 위함인가..? / 이게 DB에 바로 반영되긴 하나 확인 필요
         *
         *
         *  //이런식으로
         *  save(bookStore);
         *  book1.setBookStore(bookStore);
         *  book2.setBookStore(bookStore);
         *  save(book1);
         *  save(book2);
         *
         *  -> ex 일단 써놨는데 확인안해봄 뇌피셜.. 틀릴 가능성 높음
         */


        // @formatter:off
        log.info("One을 저장하지 않은 상태로 Many(관계의 주인이 아닌곳)에 넣고 insert 테스트");
            Many many = new Many("m1","1번 Many");
            One one1 = new One("o1", "1번 One");
            One one2 = new One("o2", "2번 One");
            many.getOnes().add(one1);
            many.getOnes().add(one2);
            relationTestService.saveMany(many);
            relationTestService.getOnes().forEach(one -> {
                log.info("현 상태 One : {}", one);
            });
            log.warn("관계의 주인이 아니기 때문에 저장된 one이 없음");
        log.info("------------------------------------\n");

        entityManager.clear();
        log.info("One을 저장하고 Many(관계의 주인이 아닌곳)에 넣고 insert 테스트");
            One one3 = new One("o3", "3번 One");
            One one4 = new One("o4", "4번 One");
            relationTestService.saveOne(one3);
            relationTestService.saveOne(one4);

            Many many4 = new Many("m4","4번 Many");
            many4.getOnes().add(one3);
            many4.getOnes().add(one4);
            relationTestService.saveMany(many4);

            Many savedMany4 = relationTestService.getMany(many4.getSeq());
            log.info("savedMany4 -> [{}]{}", savedMany4.hashCode(), savedMany4);
            log.info("현 상태의 각 One");
            relationTestService.getOnes().forEach(currentOne -> log.info("{}",currentOne));
        log.info("------------------------------------\n");

        entityManager.clear();
        log.info("Many를 저장하지 않은 상태로 One(관계의 주인)에 넣고 insert 테스트");
            log.warn("One에 cascadeType 설정 없이는 many2가 아직 존재하지 않기때문에 에러남 ");
            Many many2 = new Many("m2","2번 Many");
            one1.setMany(many2);
            relationTestService.saveOne(one1);
            relationTestService.getManys().forEach(currentMany -> log.info("{}",currentMany));
            log.warn("Many -> One 의 관계가 설정되지 않았음, 이말은 반대도 안됐다는거 아닌가..? - 아래 경우보면 들어가있는것 같음.. 왜 하나씩 밀리는 느낌이지..?");
            One savedOne = relationTestService.getOne(one1.getSeq());
            log.info("savedOne -> [{}]{}", savedOne.hashCode(), savedOne);
        log.info("------------------------------------\n");

        entityManager.clear();
        log.info("Many를 저장하고 One(관계의 주인)에 넣고 insert 테스트");
            Many many3 = new Many("m3","3번 Many");
            relationTestService.saveMany(many3);
            one2.setMany(many3);
            relationTestService.saveOne(one2);
            log.info("Many를 저장하고 넣은경우 Many에서 oneId가 저장되나, One의 ManyId가 저장되나");
            relationTestService.getManys().forEach(currentMany -> log.info("{}",currentMany));

            One savedOne2 = relationTestService.getOne(one2.getSeq());
            log.info("savedOne2 -> [{}]{}", savedOne2.hashCode(), savedOne2);
        log.info("------------------------------------\n");

        entityManager.clear();
        log.info("One을 가지고있는 Many에 추가로 One을 넣었을때 바로 반영되는건지 확인..");
            One one5 = new One("o5", "5번 One");
            One one6 = new One("o6", "6번 One");
            One one7 = new One("o7", "7번 One");

            Many many5 = new Many("m5","5번 Many");
            relationTestService.saveMany(many5);
            one5.setMany(many5);
            relationTestService.saveOne(one5);

            Many savedMany5 = relationTestService.getMany(many5.getSeq());
            log.info("savedMany5 add 전-> [{}]{}", savedMany5.hashCode(), savedMany5);
            savedMany5.getOnes().add(one6);
            savedMany5.getOnes().add(one7);
            Many savedMany5_2 = relationTestService.getMany(many5.getSeq());
            log.info("savedMany5 add 후-> [{}]{}", savedMany5.hashCode(), savedMany5);
            log.info("savedMany5_2 add 후 -> [{}]{}", savedMany5_2.hashCode(), savedMany5_2);
        log.info("------------------------------------\n");

        log.info("Delete All");
            relationTestService.deleteAllOne();
            relationTestService.deleteAllMany();
        // @formatter:on
        return "Done";
    }
}
