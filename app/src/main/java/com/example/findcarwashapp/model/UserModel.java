package com.example.findcarwashapp.model;

public class UserModel {
    public String name;
    public String lastName;
    public String email;
    public String id;
    public String uri;
    public String UserType;


    public UserModel() {
        //empty constructor
    }

    public UserModel(String name, String lastName, String email, String id, String uri, String userType) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.uri = uri;
        UserType = userType;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }
}
