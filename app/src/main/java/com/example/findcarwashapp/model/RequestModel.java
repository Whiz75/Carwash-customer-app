package com.example.findcarwashapp.model;

import java.util.List;

public class RequestModel {
    String id;
    String userId;
    String location;
    String longitude;
    String latitude;
    List<String> items;

    public RequestModel() {
        //empty constructor
    }

    public RequestModel(String id, String userId,
                        String location, String longitude, String latitude, List<String> items) {
        this.id = id;
        this.userId = userId;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
