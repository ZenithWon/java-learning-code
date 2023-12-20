package com.zenith.example.domain;

import lombok.Data;

@Data
public class Item {
    private Integer a;
    private int b;
    private String str="";
    private long[] longList=new long[10];
}
