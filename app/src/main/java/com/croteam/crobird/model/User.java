package com.croteam.crobird.model;

import com.google.firebase.database.IgnoreExtraProperties;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@IgnoreExtraProperties
public class User extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;
    private String username, password, name, dob, phone, email, job, address, searchKey, img;
    private boolean gender;
    private double price;
    private float rating, lng, lat;
    private int index;

    public static String ID = "id";
    public static String INDEX = "index";
    public static String IMG = "img";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String NAME = "name";
    public static String DOB = "dob";
    public static String PHONE = "phone";
    public static String EMAIL = "email";
    public static String JOB = "job";
    public static String GENDER = "gender";
    public static String PRICE = "price";
    public static String RATING = "rating";
    public static String ADDRESS = "address";
    public static String LONGITUDE = "lng";
    public static String LATITUDE = "lat";
    public static String SEARCH_KEY = "searchKey";

    public JSONObject toJSONObject(){
        JSONObject obj = new JSONObject();
        try {
            obj.put(ID, id);
            obj.put(USERNAME, username);
            obj.put(PASSWORD, password);
            obj.put(NAME, name);
            obj.put(DOB, dob);
            obj.put(PHONE, phone);
            obj.put(EMAIL, email);
            obj.put(GENDER, gender);
            obj.put(PRICE, price);
            obj.put(RATING, rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
