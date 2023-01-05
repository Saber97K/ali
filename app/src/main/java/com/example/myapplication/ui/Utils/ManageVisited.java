package com.example.myapplication.ui.Utils;

import java.util.ArrayList;

public class ManageVisited {
    private int id;
    private int user_id;
    private int order_id;
    private int status;
    public static ArrayList<ManageVisited> VisitsList = new ArrayList<>();

    public ManageVisited(int id, int user_id, int order_id, int status) {
        this.id = id;
        this.user_id = user_id;
        this.order_id = order_id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
