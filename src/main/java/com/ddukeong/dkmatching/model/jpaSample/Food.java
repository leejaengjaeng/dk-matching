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
@Entity(name = "food")
@ToString
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private boolean deleted;

    public Food(String name) {
        this.name = name;
    }

    /**
     * 1대1 관계에서도 관계의 주체가 존재함
     */
// 단방향 맵핑을 위한 주석처리
//    @OneToOne(mappedBy = "food")
//    private Order order;

}
