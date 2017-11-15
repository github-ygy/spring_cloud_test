package com.test.guaua.day_guaua;

import com.google.common.base.Optional;

import java.util.ArrayList;

/**
 * Created by guoyao on 2017/11/15.
 */
public class Guaua_01_Optional {

    public static void main(String[] args) {
        //testA(null);
        //testA("ygy");
        //testB("ygy");
        //testB("");
        ArrayList<String> list=testC(null, new ArrayList<String>());
        list.add(testC("ygy", "haha"));
        System.out.println(list);

    }

    //fromNullable 为Optional赋值，当T为Null则使用默认值。
    public static void testA(String str) {
        str=Optional.fromNullable(str).get();
        System.out.println("　str = " + str);
    }

    //isPresent 判断是否有值。
    public static void testB(String str) {
        System.out.println(" str = " + str + "　has value ： " + Optional.fromNullable(str).isPresent());
    }

    //or 如果不为null，则返回赋予的值。
    public static <T> T testC(T str, T defaultvalue) {
        System.out.println(" str = " + str + " value ： " + Optional.fromNullable(str).or(defaultvalue));
        return Optional.fromNullable(str).or(defaultvalue);
    }
}
