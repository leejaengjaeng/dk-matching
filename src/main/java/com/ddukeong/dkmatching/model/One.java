package com.ddukeong.dkmatching.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "one")
@ToString(exclude = "many") //양방향 연관 관계에서 ToString을 쓰려면 적어도 한쪽에선 끊어줘야함
@NoArgsConstructor
public class One {
    @Id
    @GeneratedValue
    private Long seq;

    private String id;
    private String name;

    /**
     * @see Many
     * 양방향 맵핑 관계에서는 관게의 주인이 존재함
     * 일반적으로 테이블로 생각했을때 외래키가 포함되는 테이블(모델)쪽을 주인으로 치면 됨
     * 주인이 되는 쪽에선 JoinColumn을 통해 매핑시킬곳을 지정하고
     * 주인이 아닌쪽에선 mappedBy를 사용해서 주인 모델에서 자신을 가리키는 필드명? 을 지정
     * <p>
     * CascadeType 사용 주의
     * 관계의 주인에 거냐 아니냐에 따른 동작도 생각하면서 걸어야 할듯
     * https://joont92.github.io/jpa/CascadeType-PERSIST%EB%A5%BC-%ED%95%A8%EB%B6%80%EB%A1%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EB%A9%B4-%EC%95%88%EB%90%98%EB%8A%94-%EC%9D%B4%EC%9C%A0/
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "many_id")
    private Many many;

    public One(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
