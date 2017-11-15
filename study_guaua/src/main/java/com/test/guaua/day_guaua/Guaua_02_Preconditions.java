package com.test.guaua.day_guaua;

import com.google.common.base.Preconditions;

/**
 * Created by guoyao on 2017/11/15.
 */
public class Guaua_02_Preconditions {

    public static void main(String[] args) {
        testA(null,"不能为null");
    }

    public static void testA(String key, String errorMsg) {
        Preconditions.checkNotNull(key, errorMsg);
    }

}
