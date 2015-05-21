package com.nicelife729.algo.objects.passbyvalue;

/**
 * User: rpanjrath
 * Date: 9/20/13
 * Time: 2:37 PM
 */
public class Dog {

    private String name;

    public Dog(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dog foo(Dog dog) {
        dog.setName("DEF");
        dog = new Dog("GHI");
        dog.setName("JKL");
        return dog;
    }
}
