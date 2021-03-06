package com.nicelife729.algo.objects.passbyvalue;

/**
 * Objects are passed by reference and reference itself is passed as value in Java.
 * User: rpanjrath
 * Date: 9/20/13
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class PassByValue {

    public static void main(String[] args) {
        Dog dog1 = new Dog("ABC");
        Dog dog2 = dog1.foo(dog1);
        System.out.println(dog1.hashCode() + " " + dog1.getName());
        System.out.println(dog2.hashCode() + " " + dog2.getName());
    }
}
