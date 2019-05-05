package com.croteam.crobird.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BirdCart extends RealmObject {

    @PrimaryKey
    private String id;
    private String userId, birdId;
    private Date dateCreate;

    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String BIRD_ID = "birđId";
    public static final String DATE_CREATE = "dateCreate";

    public BirdCart() {
    }

    public BirdCart(String id, String userId, String birdId, Date dateCreate) {
        this.id = id;
        this.userId = userId;
        this.birdId = birdId;
        this.dateCreate = dateCreate;
    }

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

    public String getBirdId() {
        return birdId;
    }

    public void setBirdId(String birdId) {
        this.birdId = birdId;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
}
