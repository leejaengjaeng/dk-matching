package com.ddukeong.dkmatching.model.jpaSample;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "person")
@ToString
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private boolean deleted;

    public Person(String name) {
        this.name = name;
    }

    /**
     * mappedBy가 붙은쪽은 관계의 주인이 아님 -> ReadOnly
     *
     * OneToMany <-> ManyToOne 헷갈림..
     * Person 한개(one)가 Order 여러개(many)에 맵핑된다.
     * -> OneToMany
     */
// 단방향 맵핑을 위한 주석처리
//    @OneToMany(mappedBy = "orderedPerson")
//    private List<Order> orders;
}
