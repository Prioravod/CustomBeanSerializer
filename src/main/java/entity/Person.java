package entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Person implements Serializable, Beanable {
    //private properties
    private String name;
    private String surname;
    private Car car;
    private int age;
    private BigDecimal medicalCondition;


    //no-arg constructor
    public Person(){

    }

    //public getters/setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(BigDecimal medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", medicalCondition=" + medicalCondition +
                ", car=" + car +
                '}';
    }
}