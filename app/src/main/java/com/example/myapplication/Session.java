package com.example.myapplication;

import android.app.Application;

public class Session extends Application {

    private int user_id , choice;

    public int getSomeVariable() {
        return user_id;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public void setSomeVariable(int someVariable) {
        this.user_id = someVariable;
    }
}
