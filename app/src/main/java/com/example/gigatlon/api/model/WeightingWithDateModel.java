package com.example.gigatlon.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class WeightingWithDateModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("weight")
    @Expose
    private Double weight;
    @SerializedName("height")
    @Expose
    private Double height;

    public WeightingWithDateModel() {
    }

    public WeightingWithDateModel(Integer id, Date date, Double weight, Double height) {
        super();
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.height = height;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

}