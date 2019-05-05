package com.croteam.crobird.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Reivew extends RealmObject {

    @PrimaryKey
    private String id;
    private String userId, birdId, content, username;
    private float rating;
    private long dateCreate;

    public static String ID = "ID";
    public static String USER_ID = "userId";
    public static String USER_NAME = "username";
    public static String BIRD_ID = "birdId";
    public static String CONTENT = "content";
    public static String RATING = "rating";
    public static String DATE_CREATE = "dateCreate";

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirdId() {
        return birdId;
    }

    public void setBirdId(String birdId) {
        this.birdId = birdId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }
}
