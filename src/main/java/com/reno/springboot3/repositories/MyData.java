package com.reno.springboot3.repositories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "mydata")
@NamedQueries(
//        @NamedQuery(
//                name = "findWithName",
//                query = "from MyData where name like :fname"
//        ),
        value = @NamedQuery(
                name = "findByAge",
                query = "from MyData where age > :min and age < :max"
        )
)

public class MyData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @NotNull
    @Getter
    @Setter
    private long id;

    @Column(length = 50, nullable = false)
    @NotEmpty(message = "공백 불가")
    @Getter
    @Setter
    private String name;

    @Column(length = 200, nullable = true)
    @Email(message = "메일 주소만")
    @Getter
    @Setter
    private String mail;

    @Column(nullable = true)
    @Min(value = 0, message = "0세 이상")
    @Max(value = 200, message = "200세 이하")
    @Getter
    @Setter
    private Integer age;

    @Column(nullable = true)
    @Phone(onlyNumber = true)
    @Getter
    @Setter
    private String memo;

}
