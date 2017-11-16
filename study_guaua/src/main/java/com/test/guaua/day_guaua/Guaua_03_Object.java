package com.test.guaua.day_guaua;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.test.guaua.dataobject.Person;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by guoyao on 2017/11/16.
 */
public class Guaua_03_Object {

    public static void main(String[] args) {
        //testA();
        testB();
    }


    //ComparisonChain 比较链判断
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

    //比较
    public static void testB() {
        System.out.println(Objects.equal(null, "abc"));
        System.out.println(Objects.equal("abc", "abc"));
    }
}

