package com.romy.coffeeproject;


public class Review {
    private String userName;
    private String reviewText;
    private float rating;  // Use float for compatibility with RatingBar

    // Constructor
    public Review(String userName, String reviewText, float rating) {
        this.userName = userName;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    // Getters
    public String getUserName() {
        return userName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public float getRating() {
        return rating;
    }
}
