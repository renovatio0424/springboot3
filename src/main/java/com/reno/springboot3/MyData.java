package com.reno.springboot3;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "mydata")
public class MyData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @Getter @Setter private long id;

    @Column(length = 50, nullable = false)
    @Getter @Setter private String name;

    @Column(length = 200, nullable = true)
    @Getter @Setter private String mail;

    @Column(nullable = true)
    private Integer age;

    @Column(nullable = true)
    @Getter @Setter private String memo;


}
