package com.example.gigatlon.api.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntity {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name="username")
    public String username;

    public UserEntity(){}

    public UserEntity(String username) {
        this.username = username;
    }
}