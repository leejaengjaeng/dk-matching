package com.ddukeong.dkmatching.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "testAccount")
public class TestAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long testAccountSeq;

    private String id;
    private String name;
}
