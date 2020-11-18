package com.example.gigatlon.domain;

import java.util.Date;
import java.util.Objects;

public class Weighting {
    private Integer id;
    private Date date;
    private Double weight;
    private Double height;

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

    public Weighting(Integer id, Date date, Double weight, Double height) {
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.height = height;
    }

    public Weighting(Double weight, Double height) {
        this.weight = weight;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weighting weighting = (Weighting) o;
        return Objects.equals(getId(), weighting.getId());
    }
}