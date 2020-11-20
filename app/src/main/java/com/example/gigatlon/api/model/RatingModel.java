package com.example.gigatlon.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingModel {

    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("review")
    @Expose
    private String review;

    public RatingModel() {
    }

    public RatingModel(Integer score, String review) {
        super();
        this.score = score;
        this.review = review;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}