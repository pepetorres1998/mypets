package com.example.mypets2.models;

public class Cat extends Pet {
    private String name;
    private int age;

    private long id; //Database ID

    public Cat(String name, int age){
        super(name, age);
    }

    //Constructor when initialized from Database
    public Cat(String name, int age, long id){
        super(name, age, id);
    }

    @Override
    public String toString() {
        return "Gato{" +
                "nombre='" + name + '\'' +
                ", edad=" + age +
                '}';
    }
}
