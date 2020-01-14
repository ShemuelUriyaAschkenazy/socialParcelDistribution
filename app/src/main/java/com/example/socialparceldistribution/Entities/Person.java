package com.example.socialparceldistribution.Entities;

import androidx.annotation.NonNull;

public class Person {
    @NonNull
    @Override
    public String toString() {
        return name+" "+id+" "+phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(String id, String phone, String name) {
        this.id = id;
        this.phone = phone;
        this.name = name;
    }

    private String id;
    private String phone;
    private String name;
}
