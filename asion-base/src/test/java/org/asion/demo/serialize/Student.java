package org.asion.demo.serialize;

import java.io.Serializable;

/**
 * Title:学生类
 * Description:实现序列化接口的学生类
 * Copyright: copyright(c) 2012
 * Filename: Student.java
 *
 * @author Asion
 * @version 1.0
 */
public class Student implements Serializable {
    private String name;
    private char sex;
    private int year;
    private double gpa;

    public Student() {

    }

    Student(String name, char sex, int year, double gpa) {
        this.name = name;
        this.sex = sex;
        this.year = year;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}