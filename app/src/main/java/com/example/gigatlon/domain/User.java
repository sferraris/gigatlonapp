package com.example.gigatlon.domain;

import java.util.Date;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String fullName;
    private String gender;
    private Date birthdate;
    private String email;
    private String phone;
    private String avatarUrl;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public User(String username, String fullName, String gender, Date birthdate, String email, String avatarUrl) {
        this(0, username, fullName, gender, birthdate, email, avatarUrl);
    }

    public User(Integer id, String username, String fullName, String gender, Date birthdate, String email, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = "000000000";
        this.avatarUrl = avatarUrl;
    }

    public User(String username, String password, String fullName, String gender, Date birthdate, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = "000000000";
        this.avatarUrl = "STRING RANDOM"; //TODO STRING RANDOM
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }
}