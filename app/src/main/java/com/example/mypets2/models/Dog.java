package com.example.mypets2.models;

public class Dog extends Pet {
    private String name;
    private int age;

    private long id; //Database ID

    public Dog(String name, int age){
        super(name, age);
    }

    //Constructor when initialized from Database
    public Dog(String name, int age, long id){
        super(name, age, id);
    }

    @Override
    public String toString() {
        return "Perro{" +
                "nombre='" + name + '\'' +
                ", edad=" + age +
                '}';
    }
}
