package com.example.gigatlon.db.entity;

import androidx.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;


@Entity(tableName = "User", indices = {@Index("id")}, primaryKeys = {"id"})
public class UserEntity {
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

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

    public UserEntity(int id, String username, String fullName, String gender, Date birthdate, String email, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate.toString();
        this.email = email;
        this.avatarUrl = avatarUrl;

    }
}