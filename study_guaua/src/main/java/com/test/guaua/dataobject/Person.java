package com.test.guaua.dataobject;

import lombok.Data;

/**
 * Created by guoyao on 2017/11/16.
 */
@Data
public class Person {

    public Person(String name, Integer age, Integer sex) {
        this.name=name;
        this.age=age;
        this.sex=sex;
    }

    private String name;

    private Integer age;

    private Integer sex;
}
