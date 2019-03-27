package com.croteam.crobird.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BirdCart extends RealmObject {

    @PrimaryKey
    private String id;
    private String userId, birđId;
    private Date dateCreate;

    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String BIRD_ID = "birđId";
    public static final String DATE_CREATE = "dateCreate";

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

    public String getBirđId() {
        return birđId;
    }

    public void setBirđId(String birđId) {
        this.birđId = birđId;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
}
