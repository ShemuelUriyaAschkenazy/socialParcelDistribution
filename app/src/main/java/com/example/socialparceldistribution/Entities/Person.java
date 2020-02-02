package com.example.socialparceldistribution.Entities;

import androidx.annotation.NonNull;

public class Person {
    @NonNull
    @Override
    public String toString() {
        return name+" "+id+" "+email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public Person(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String id;
    private String email;
    private String name;
}
