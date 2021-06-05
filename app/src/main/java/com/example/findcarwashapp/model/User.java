package com.example.findcarwashapp.model;

public class User {
    public String name;
    public String lastName;
    public String email;
    public String id;

    public User() {
        //empty constructor
    }

    public User(String name, String lastName, String email, String id) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
