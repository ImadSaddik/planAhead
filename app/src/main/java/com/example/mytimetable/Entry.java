package com.example.mytimetable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "moduleElement")
public class Entry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int endWeek;
    private int startWeek;
    private int duration; // in hours
    private int dayRanking;
    private String startingFrom;
    private String nameOfElement;
    private String type; // is it course or lab
    private String day;

    public Entry(int endWeek, int startWeek, int duration, String startingFrom,
                 String nameOfElement,
                 String type,
                 String day,
                 int dayRanking) {
        this.dayRanking = dayRanking;
        this.endWeek = endWeek;
        this.day = day;
        this.startWeek = startWeek;
        this.duration = duration;
        this.type = type;
        this.startingFrom = startingFrom;
        this.nameOfElement = nameOfElement;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getDayRanking() {
        return dayRanking;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public int getDuration() {
        return duration;
    }

    public String getStartingFrom() {
        return startingFrom;
    }

    public String getNameOfElement() {
        return nameOfElement;
    }

    public String getType() {
        return type;
    }

    public String getDay() {
        return day;
    }
}
