package com.example.myapplication.ui.Utils;

import java.util.ArrayList;
import java.lang.String;

public class ManageTopUps {

    public static ArrayList<ManageTopUps> TopUpsList = new ArrayList<>();
    private int id;
    private int wallet_id;
    private float value;
    private String date;


    public ManageTopUps(int id, int wallet_id, float value, String date) {
        this.id = id;
        this.wallet_id = wallet_id;
        this.value = value;
        this.date = date;
    }

    public static ArrayList<ManageTopUps> getWalletList() {
        return TopUpsList;
    }

    public int getId() {
        return id;
    }

    public int getWallet_id() {
        return wallet_id;
    }

    public float getValue() {
        return value;
    }

    public String getString() {
        return date;
    }

    public static void setWalletList(ArrayList<ManageTopUps> walletList) {
        TopUpsList = walletList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWallet_id(int wallet_id) {
        this.wallet_id = wallet_id;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setString(String date) {
        this.date = date;
    }
}


