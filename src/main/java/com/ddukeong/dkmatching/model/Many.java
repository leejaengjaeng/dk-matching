package com.ddukeong.dkmatching.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "many")
@ToString
@NoArgsConstructor
public class Many {
    @Id
    @GeneratedValue
    private Long seq;

    private String id;
    private String name;

    /**
     * @see One
     * 양방향 맵핑 관계에서는 관게의 주인이 존재함
     * 일반적으로 테이블로 생각했을때 외래키가 포함되는 테이블(모델)쪽을 주인으로 치면 됨
     * 주인이 되는 쪽에선 JoinColumn을 통해 매핑시킬곳을 지정하고
     * 주인이 아닌쪽에선 mappedBy를 사용해서 주인 모델에서 자신을 가리키는 필드명? 을 지정
     */
    @OneToMany(mappedBy = "many")
    private List<One> ones;

    public Many(String id, String name) {
        this.id = id;
        this.name = name;
        this.ones = new ArrayList<>();
    }
}
