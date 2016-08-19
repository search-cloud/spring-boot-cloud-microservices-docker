package org.asion.sample.assertj.demo;

import java.util.Date;

/**
 * @author Asion.
 * @since 16/7/2.
 */
public class Demo {
    private Long id;
    private String code;
    private String name;
    private int age;
    private Double money;
    private int status;
    private Date createdAt;

    public Demo() {}

    public Demo(Long id, String code, String name, int age, Double money, int status, Date createdAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.age = age;
        this.money = money;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
