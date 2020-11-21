package com.example.gigatlon.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "Weighting", indices = {@Index("id")}, primaryKeys = {"id"})
public class WeightingEntity {

    @NonNull
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "weight")
    public double weight;
    @ColumnInfo(name = "height")
    public double height;

    public WeightingEntity(){}

    public WeightingEntity(int id, Date date, double weight, double height) {
        this.id = id;
        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
        String s = form.format(date);
        this.date = s;
        this.weight = weight;
        this.height = height;
    }
}