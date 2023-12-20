package com.zenith.example;

import com.zenith.example.domain.Animal;
import com.zenith.example.domain.Tiger;

/**
 * @author ZenithWon
 * @date 2023/12/20
 * @description 多态问题：父类引用子类对象
 */

public class ExampleTwo {
    public static void main(String[] args) {
        Animal animal=new Tiger();
        Tiger tiger=(Tiger) animal;
        System.out.println("=========Animal=========");
        animal.eat();//调用子类覆盖的方法
        animal.run();//调用子类未覆盖的方法
        //animal无法调用tiger的jump方法

        System.out.println("=========Tiger=========");
        tiger.eat();
        tiger.jump();
        tiger.run();
    }
}
