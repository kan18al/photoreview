package com.example.photoReview.models;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Rating implements Serializable {
    public String photoId;
    public String uid;
    public Float rate;

    public Rating() {
    }

    public Rating(String photoId, String uid, Float rate) {
        this.photoId = photoId;
        this.uid = uid;
        this.rate = rate;
    }
}
