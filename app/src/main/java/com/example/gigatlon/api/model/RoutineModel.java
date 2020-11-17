package com.example.gigatlon.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("dateCreated")
    @Expose
    private Integer dateCreated;
    @SerializedName("averageRating")
    @Expose
    private Integer averageRating;
    @SerializedName("isPublic")
    @Expose
    private Boolean isPublic;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("creator")
    @Expose
    private CreatorModel creatorModel;
    @SerializedName("category")
    @Expose
    private CategoryModel category;

    public RoutineModel() {
    }

    public RoutineModel(Integer id, String name, String detail, Integer dateCreated, Integer averageRating, Boolean isPublic, String difficulty, CreatorModel creatorModel, CategoryModel category) {
        super();
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.dateCreated = dateCreated;
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.creatorModel = creatorModel;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Integer dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public CreatorModel getCreatorModel() {
        return creatorModel;
    }

    public void setCreatorModel(CreatorModel creatorModel) {
        this.creatorModel = creatorModel;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

}