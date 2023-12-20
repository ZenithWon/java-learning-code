package com.zenith.example;

/**
 * @author ZenithWon
 * @date 2023/12/20
 * @description 包装类缓存测试
 */

public class ExampleThree {
    public static void main(String[] args) {
        String str1="aa";
        String str2="aa";
        String str3=new String("aa");
        System.out.println("str1 compare to str2: "+(str1==str2));
        System.out.println("str1 compare to str3: "+(str1==str3));

        Integer integer1=127;
        Integer integer2=127;
        Integer integer3=new Integer(127);
        System.out.println("integer1 compare to integer2: "+(integer1==integer2));
        System.out.println("integer1 compare to integer3: "+(integer1==integer3));

        Integer integer4=128;
        Integer integer5=128;
        Integer integer6=new Integer(128);
        System.out.println("integer4 compare to integer5: "+(integer4==integer5));
        System.out.println("integer4 compare to integer6: "+(integer4==integer6));
    }
}
