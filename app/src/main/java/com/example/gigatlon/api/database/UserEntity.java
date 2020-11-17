package com.example.gigatlon.api.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gigatlon.api.model.User;

import java.util.Date;

@Entity
public class UserEntity {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name="username")
    public String username;

    @ColumnInfo(name="fullName")
    public String fullName;

    @ColumnInfo(name="gender")
    public String gender;

    @ColumnInfo(name="birthdate")
    public String birthdate;

    @ColumnInfo(name="email")
    public String email;

    @ColumnInfo(name="avatarUrl")
    public String avatarUrl;

    public UserEntity(){}

    public UserEntity(User user) {
        username = user.getUsername();
        fullName = user.getFullName();
        gender = user.getGender();
        birthdate = user.getBirthdate().toString();
        email = user.getEmail();
        avatarUrl = user.getAvatarUrl();
    }
}