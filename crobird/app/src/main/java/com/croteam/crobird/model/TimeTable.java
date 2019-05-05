package com.croteam.crobird.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TimeTable extends RealmObject {

    @PrimaryKey
    private String id;
    private String userId, dayOfWeek;
    int fromTime, toTime;

    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String DAY_OF_WEEK = "id";
    public static final String FROM_TIME = "id";
    public static final String TO_TIME = "id";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getFromTime() {
        return fromTime;
    }

    public void setFromTime(int fromTime) {
        this.fromTime = fromTime;
    }

    public int getToTime() {
        return toTime;
    }

    public void setToTime(int toTime) {
        this.toTime = toTime;
    }
}
