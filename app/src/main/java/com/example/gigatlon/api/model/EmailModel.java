package com.example.gigatlon.api.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailModel {

    @SerializedName("email")
    @Expose
    private String email;


    public EmailModel() {
    }


    public EmailModel(String email) {
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}