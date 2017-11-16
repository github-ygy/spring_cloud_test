package com.test.guaua.day_guaua;

import com.google.common.collect.ComparisonChain;
import lombok.Data;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by guoyao on 2017/11/16.
 */
public class Guaua_03_Object {

    public static void main(String[] args) {
        testA();
    }


    //ComparisonChain 比较
    public static void testA() {
        Set<Person> primarySet=new TreeSet<>(
                (x,y)->{
                    return ComparisonChain.start()
                            .compare(y.getAge(), x.getAge())
                            .compare(y.getName(), x.getName())
                            .compare(x.getSex(), y.getSex())
                            .result();
                }
        );
        primarySet.add(new Person("a", 14, 1));
        primarySet.add(new Person("a", 13, 1));
        primarySet.add(new Person("b", 14, 1));
        primarySet.add(new Person("c", 13, 1));
        primarySet.add(new Person("c", 13, 0));
        System.out.println(primarySet);

    }
}
@Data
class Person{

    public Person(String name, Integer age, Integer sex) {
        this.name = name ;
        this.age = age;
        this.sex =sex ;
    }

    private String name;

    private Integer age ;

    private Integer sex ;



}
