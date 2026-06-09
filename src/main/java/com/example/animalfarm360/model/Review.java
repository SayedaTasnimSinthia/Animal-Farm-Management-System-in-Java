package com.example.animalfarm360.model;

public class Review {
    private String name;
    private String feedback;
    private int rating;

    public Review() {

    }

    public Review(String name, String feedback, int rating) {
        this.name = name;
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
