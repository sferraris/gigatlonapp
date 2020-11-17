package com.example.gigatlon.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatorModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("dateCreated")
    @Expose
    private Integer dateCreated;
    @SerializedName("dateLastActive")
    @Expose
    private Integer dateLastActive;

    public CreatorModel() {
    }

    public CreatorModel(Integer id, String username, String gender, String avatarUrl, Integer dateCreated, Integer dateLastActive) {
        super();
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.dateCreated = dateCreated;
        this.dateLastActive = dateLastActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Integer dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getDateLastActive() {
        return dateLastActive;
    }

    public void setDateLastActive(Integer dateLastActive) {
        this.dateLastActive = dateLastActive;
    }

}