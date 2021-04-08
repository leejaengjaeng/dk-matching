package com.ddukeong.dkmatching.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "testAccount")
@ToString
@NoArgsConstructor
public class TestAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long testAccountSeq;

    private String id;
    private String name;

    public TestAccount(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
