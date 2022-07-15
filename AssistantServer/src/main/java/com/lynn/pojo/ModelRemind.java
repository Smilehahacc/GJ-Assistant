package com.lynn.pojo;

public class ModelRemind {
    int remind_id;
    int user_id;
    String phone_model;
    int price_sign;
    int price_now;
    int sign_date;

    public int getRemind_id() {
        return remind_id;
    }

    public void setRemind_id(int remind_id) {
        this.remind_id = remind_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPhone_model() {
        return phone_model;
    }

    public void setPhone_model(String phone_model) {
        this.phone_model = phone_model;
    }

    public int getPrice_sign() {
        return price_sign;
    }

    public void setPrice_sign(int price_sign) {
        this.price_sign = price_sign;
    }

    public int getPrice_now() {
        return price_now;
    }

    public void setPrice_now(int price_now) {
        this.price_now = price_now;
    }

    public int getSign_date() {
        return sign_date;
    }

    public void setSign_date(int sign_date) {
        this.sign_date = sign_date;
    }
}
