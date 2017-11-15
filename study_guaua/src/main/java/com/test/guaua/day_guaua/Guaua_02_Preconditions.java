package com.test.guaua.day_guaua;

import com.google.common.base.Preconditions;
import org.springframework.util.StringUtils;

/**
 * Created by guoyao on 2017/11/15.
 */
public class Guaua_02_Preconditions {

    public static void main(String[] args) {
        //testA(null,"不能为null");
        //testA("ygy","不能为null");
        testB("","key不能为空或者null");
    }

    //checkNotNull  判断是否为null，为null则抛出异常
    public static void testA(String key, String errorMsg) {
        Preconditions.checkNotNull(key, errorMsg);
    }

    public static void testB(String key, String errorMsg) {
        Preconditions.checkArgument(!StringUtils.isEmpty(key), errorMsg);
    }





}
