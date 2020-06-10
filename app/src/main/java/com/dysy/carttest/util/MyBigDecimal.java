package com.dysy.carttest.util;


import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MyBigDecimal {

    public static String add(double d1, double d2) { // 进行加法运算
        String st;
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        st = df.format(b1.add(b2).doubleValue());
        return st;
    }
}