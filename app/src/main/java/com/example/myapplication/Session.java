package com.example.myapplication;

import android.app.Application;

public class Session extends Application {

    private int user_id;

    public int getSomeVariable() {
        return user_id;
    }


    public void setSomeVariable(int someVariable) {
        this.user_id = someVariable;
    }
}
