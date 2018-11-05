package com.mytest.java.model;

public class Person{

    private String name;
    private String date;
    private int number;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", number=" + number +
                '}';
    }

    public Person(String name, String date, int number) {
        this.name = name;
        this.date = date;
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getNumber() {
        return number;
    }
}
