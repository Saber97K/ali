package com.example.myapplication.ui.Utils;

import android.graphics.Bitmap;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Stack;

public class UsersManage {

    public static ArrayList<UsersManage> UsersList = new ArrayList<>();

    private int id;
    private String role;
    private String email;
    private String password;
    private String birthday;
    private String name;
    private String location;
    private String gender;
    private String phoneNumber;
    private int activeOrder;
    private Bitmap image;

    public UsersManage(int id, String role, String email, String password, String birthday, String name, String location, String gender, String phoneNumber, int activeOrder, Bitmap image) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.name = name;
        this.location = location;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.activeOrder = activeOrder;
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getActiveOrder() {
        return activeOrder;
    }

    public void setActiveOrder(int activeOrder) {
        this.activeOrder = activeOrder;
    }

    public static UsersManage getUsersForID(int passedUsersManageID)
    {
        for (UsersManage user : UsersList)
        {
            if(user.getId() == passedUsersManageID)
                return user;
        }

        return null;
    }

    public static ArrayList<UsersManage> UsersAll()
    {
        ArrayList<UsersManage> users = new ArrayList<>();
        for(UsersManage user : UsersList)
        {
            users.add(user);
        }

        return users;
    }

    public static ArrayList<UsersManage> getUsersList() {
        return UsersList;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setUsersList(ArrayList<UsersManage> usersList) {
        UsersList = usersList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
