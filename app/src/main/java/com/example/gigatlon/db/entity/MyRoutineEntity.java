package com.example.gigatlon.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;

@Entity(tableName = "MyRoutine", indices = {@Index("id")}, primaryKeys = {"id"})
public class MyRoutineEntity {

    @NonNull
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "detail")
    public String detail;
    @ColumnInfo(name = "dateCreated")
    public String dateCreated;
    @ColumnInfo(name = "averageRating")
    public double averageRating;
    @ColumnInfo(name = "isPublic")
    public Boolean isPublic;
    @ColumnInfo(name = "difficulty")
    public String difficulty;
    @ColumnInfo(name = "creator")
    public String creator;

    public MyRoutineEntity(){}

    public MyRoutineEntity(int id, String name, String detail, Date dateCreated, double averageRating, Boolean isPublic, String difficulty, String creator) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.dateCreated = dateCreated.toString();
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.creator = creator;
    }
}