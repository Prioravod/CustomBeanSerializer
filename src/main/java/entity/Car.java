package entity;

import java.io.Serializable;
import java.util.List;

public class Car implements Serializable, Beanable {

    private String mark;
    private String model;
    private int year;

    public Car() { }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}

