package com.example.mypets2.models;

public class Pet {
    private String name;
    private int age;

    private long id; //Database ID

    public Pet(String name, int age){
        this.name = name;
        this.age = age;
    }

    //Constructor when initialized from Database
    public Pet(String name, int age, long id){
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + name + '\'' +
                ", edad=" + age +
                '}';
    }
}
