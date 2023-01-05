package com.example.myapplication.ui.Utils;

import java.util.ArrayList;

public class RatingManage {

    private int id;
    private int to_user;
    private int from_user;
    private int value;
    private String desc;
    public static ArrayList<RatingManage> ratingsArrayList = new ArrayList<>();

    public RatingManage(int id, int to_user, int from_user, int value , String desc) {
        this.id = id;
        this.desc = desc;
        this.to_user = to_user;
        this.from_user = from_user;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public int getTo_user() {
        return to_user;
    }

    public int getFrom_user() {
        return from_user;
    }

    public int getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTo_user(int to_user) {
        this.to_user = to_user;
    }

    public void setFrom_user(int from_user) {
        this.from_user = from_user;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
