package com.example.myapplication.ui.Utils;

import java.util.ArrayList;


public class ManageWallet {

    public static ArrayList<ManageWallet> WalletList = new ArrayList<>();
    private int id;
    private String bankAccountNumber;
    private int user_id;
    private float balance;
    private String password;

    public ManageWallet(int id, String bankAccountNumber, int user_id, float balance, String password) {
        this.id = id;
        this.bankAccountNumber = bankAccountNumber;
        this.user_id = user_id;
        this.balance = balance;
        this.password = password;
    }

    public static ArrayList<ManageWallet> getWalletList() {
        return WalletList;
    }

    public int getId() {
        return id;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public int getUser_id() {
        return user_id;
    }

    public float getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public static void setWalletList(ArrayList<ManageWallet> walletList) {
        WalletList = walletList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

