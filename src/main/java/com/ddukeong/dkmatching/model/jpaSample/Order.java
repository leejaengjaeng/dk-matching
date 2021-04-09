package com.ddukeong.dkmatching.model.jpaSample;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "food_order") // order는 예약어기 때문에 사용하면 안됨
@ToString                    // 단방향 맵핑만 존재함으로 toString 써도 루프에 안빠짐
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 각 관계애서 테이블을 기준으로 생각했을때 FK를 가지게 될 테이블이 관계의 주인
     * 관계도 그려보면 어디가 주인이어야 하는지 보임
     */

    /**
     * OneToMany <-> ManyToOne 헷갈림..
     * Order 여러개(many)가 Person 한개(one)에 맵핑된다.
     * -> ManyToOne
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person orderedPerson;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "food_id")
    private Food food;

    private int count;

    /**
     * ManyToMany 관계는 Entity를 하나 별도로 만들고 독립적인 id(ex. AI값)을 갖도록 하는것이 좋음
     * @JoinTable을 @JoinColumn 위치에 사용해서 만들수도 있지만..
     * 드러나지 않는 테이블이 생겨서 동작하는 쿼리를 알아보기 힘들어지고, 키가 복합키로 설정될꺼라서 row 관리가 힘듬
     */
}
