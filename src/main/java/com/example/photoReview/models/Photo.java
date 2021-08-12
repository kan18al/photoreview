package com.example.photoReview.models;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class Photo implements Serializable {

    public String id;
    public String photo;
    public Double latitude;
    public Double longitude;
    public String comment;
    public String EmailUser;
    public String avaUserUri;
    public Date currentDate;

    private String key;
    private Float rating;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Photo() {
    }

    public Photo(String id, String photo, Double latitude, Double longitude, String comment, String EmailUser, String avaUserUri, Date currentDate) {
        this.id = id;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.comment = comment;
        this.EmailUser = EmailUser;
        this.avaUserUri = avaUserUri;
        this.currentDate = currentDate;
    }

    @Override
    public String toString() {
        return id + " " + comment;
    }
}
