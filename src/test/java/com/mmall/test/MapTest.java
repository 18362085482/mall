package com.mmall.test;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapTest {

    @Test
    public void test1(){
        Map<String,String> paramsMap = Maps.newHashMap();
        paramsMap.put("name","ch");
        paramsMap.put("age","15");
        paramsMap.put("birth","1993");
        paramsMap.put("date","0828");
        for(Iterator iterator = paramsMap.keySet().iterator();iterator.hasNext();){
            String key = (String) iterator.next();
            System.out.println( new StringBuilder().append("key:").append(key).append(",and value:").append(paramsMap.get(key)).toString());
        }
    }

    @Test
    public void test2(){
        Map<String,String> paramsMap = Maps.newHashMap();
        paramsMap.put("name","ch");
        paramsMap.put("age","15");
        paramsMap.put("birth","1993");
        paramsMap.put("date","0828");
        Set<String> keySet=paramsMap.keySet();
        for (String key:keySet){
            String value=paramsMap.get(key);

        }
    }



}
