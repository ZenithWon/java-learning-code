package com.zenith.example.domain;

public class Tiger extends Animal{
    @Override
    public void eat() {
        System.out.println("Tiger eat!");
    }

    public void jump(){
        System.out.println("Tiger jump!");
    }
}
