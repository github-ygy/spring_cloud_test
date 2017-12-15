package com.test.guaua.day_guaua;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.test.guaua.dataobject.Person;

/**
 * Created by guoyao on 2017/11/16.
 */
public class Guaua_04_Cache {

    public static void main(String[] args) throws Exception  {
        LoadingCache<String, Person> loadingCache=testA();
        loadingCache.put("ygy",new Person("ygy",24,1));
        System.out.println(loadingCache.getUnchecked("haha"));
        System.out.println(loadingCache.getIfPresent("haha"));
    }

    public static LoadingCache<String,Person> testA() {
       LoadingCache<String,Person>  loadingCache = CacheBuilder.newBuilder().build(
                new CacheLoader<String, Person>() {
                    @Override
                    public Person load(String key)  {
                        //可以从数据库查询
                        return null;
                    }
                }
        );
       return loadingCache;
    }
}
