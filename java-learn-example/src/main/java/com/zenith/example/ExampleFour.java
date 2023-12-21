package com.zenith.example;

import com.zenith.example.domain.Tiger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExampleFour {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Tiger tiger=new Tiger();
        Class<?> tigerClass = tiger.getClass();

        // Get all methods including private and protected methods, but they must be declared in this class
        Method [] allMethods=tigerClass.getDeclaredMethods();
        System.out.println("======= Self methods =======");
        for(Method item:allMethods){
            System.out.println(item.toString());
        }

        // Get all methods, which including methods that extends from basic class
        Method [] publicMethods=tigerClass.getMethods();
        System.out.println("======= All public methods =======");
        for(Method item:publicMethods){
            System.out.println(item.toString());
        }

        // Reflect could execute private method
        System.out.println("======= Uses private method =======");
        Method method=tigerClass.getDeclaredMethod("privateChild");
        method.setAccessible(true);
        method.invoke(tiger);

    }
}
