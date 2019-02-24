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
    private String username, password, name, dob, phone, email;
    private boolean gender;
    private double price;
    private float rating;

    public static String ID = "id";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String NAME = "name";
    public static String DOB = "dob";
    public static String PHONE = "phone";
    public static String EMAIL = "email";
    public static String GENDER = "gender";
    public static String PRICE = "price";
    public static String RATING = "rating";

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
}
