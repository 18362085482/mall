package com.mmall.test;

import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalTest {

    @Test
    public void test1(){
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
    }

    @Test
    public void test2(){
        System.out.println(new BigDecimal(0.05).add(new BigDecimal(0.01)));
    }

    @Test
    public void test3(){
        System.out.println(new BigDecimal("0.05").multiply(new BigDecimal("0.01")));
    }
}
