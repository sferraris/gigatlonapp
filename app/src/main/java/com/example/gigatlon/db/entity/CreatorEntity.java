package com.example.gigatlon.db.entity;

import androidx.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "Creator", indices = {@Index("id")}, primaryKeys = {"id"})
public class CreatorEntity {
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name="username")
    public String username;

    @ColumnInfo(name="gender")
    public String gender;

    @ColumnInfo(name="avatarUrl")
    public String avatarUrl;

    public CreatorEntity(){}

    public CreatorEntity(int id, String username, String gender, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
    }
}