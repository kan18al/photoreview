package com.example.photoReview.models;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
public class Tour implements Serializable {
    public String description;
    public String image;
    public Double price;
    public Map<String, String> review;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tour() {
    }

    public Tour(String description, String image, Double price, Map<String, String> review) {
        this.description = description;
        this.image = image;
        this.price = price;
        this.review = review;
    }
}
