package com.example.findcarwashapp.model;

import java.util.List;

public class Request {
    String id;
    String userId;
    String date;
    String requester;
    String location;
    String longitude;
    String latitude;
    String profile;
    Boolean status;
    List<String> items;

    public Request() {
        //empty constructor
    }

    public Request(String id, String userId, String date, String requester,
                   String location, String longitude, String latitude, String profile,
                   Boolean status, List<String> items) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.requester = requester;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.profile = profile;
        this.status = status;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
