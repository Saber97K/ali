package com.example.myapplication.ui.Utils;

import com.example.myapplication.Session;

import java.util.ArrayList;

public class OrdersManage {

        public static ArrayList<OrdersManage> orderArrayList = new ArrayList<>();
        private int id;
        private int user_id;
        private int accepted_user_id;
        private String type;
        private String title;
        private String description;
        private double price;
        private String date;
        private int status;
        private String category;

    public OrdersManage(int id, int user_id, int accepted_user_id, String type, String title, String description, double price, String date, int status, String category) {
        this.id = id;
        this.user_id = user_id;
        this.accepted_user_id = accepted_user_id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.date = date;
        this.status = status;
        this.category = category;
    }

    public static OrdersManage getOrderForID(int passedOrderID)
        {
            for (OrdersManage order : orderArrayList)
            {
                if(order.getId() == passedOrderID)
                    return order;
            }

            return null;
        }

        public static ArrayList<OrdersManage> OngoingOrder(int current)
        {
            ArrayList<OrdersManage> Ongoing = new ArrayList<>();
            for(OrdersManage order : orderArrayList)
            {
                if(order.getStatus() == 0 && order.getUser_id() == current && order.getAccepted_user_id()!= -1)
                    Ongoing.add(order);
            }

            return Ongoing;
        }

    public static ArrayList<OrdersManage> OngoingOrderAvailable()
    {
        ArrayList<OrdersManage> Ongoing = new ArrayList<>();
        for(OrdersManage order : orderArrayList)
        {
            if(order.getStatus() == 0 && order.getAccepted_user_id() == -1)
                Ongoing.add(order);
        }

        return Ongoing;
    }

    public static ArrayList<OrdersManage> OngoingOrderAvailable(int cat_id)
    {
        String categoryManage = "";
        ArrayList<OrdersManage> Ongoing = new ArrayList<>();
        for (int i =0 ; i < CategoryManage.categoryManages.size(); i++) {
            if(CategoryManage.categoryManages.get(i).getId() == cat_id){
                categoryManage = CategoryManage.categoryManages.get(i).getText();
            }
        }

        for(OrdersManage order : orderArrayList)
        {
            if(order.getStatus() == 0 && order.getAccepted_user_id() == -1  && order.getCategory().equals(categoryManage))
                Ongoing.add(order);
        }

        return Ongoing;
    }

    public static ArrayList<OrdersManage> CompletedOrder(int current)
    {
        ArrayList<OrdersManage> Completed = new ArrayList<>();
        for(OrdersManage order : orderArrayList)
        {
            if(order.getStatus() == 1 && order.getUser_id() == current)
                Completed.add(order);
        }

        return Completed;
    }
    public static ArrayList<OrdersManage> CompletedOrderByThisPerson(int thisUser)
    {
        ArrayList<OrdersManage> Completed = new ArrayList<>();
        for(OrdersManage order : orderArrayList)
        {
            if(order.getStatus() == 1 && order.getAccepted_user_id() == thisUser)
                Completed.add(order);
        }

        return Completed;
    }
    public static ArrayList<OrdersManage> OngoingOrderByThisPerson(int thisUser)
    {
        ArrayList<OrdersManage> Completed = new ArrayList<>();
        for(OrdersManage order : orderArrayList)
        {
            if(order.getStatus() == 0 && order.getAccepted_user_id() == thisUser)
                Completed.add(order);
        }

        return Completed;
    }
    public static ArrayList<OrdersManage> CancelledOrderByThisPerson(int thisUser)
    {
        ArrayList<OrdersManage> Completed = new ArrayList<>();
        for(OrdersManage order : orderArrayList)
        {
            if(order.getStatus() == 2 && order.getAccepted_user_id() == thisUser)
                Completed.add(order);
        }

        return Completed;
    }
    public static ArrayList<OrdersManage> CancelledOrder(int current)
    {
        ArrayList<OrdersManage> Cancelled = new ArrayList<>();
        for(OrdersManage order : orderArrayList)
        {
            if(order.getStatus() == 2 && order.getUser_id() == current)
                Cancelled.add(order);
        }

        return Cancelled;
    }

    public int getAccepted_user_id() {
        return accepted_user_id;
    }

    public void setAccepted_user_id(int accepted_user_id) {
        this.accepted_user_id = accepted_user_id;
    }

    public static ArrayList<OrdersManage> getOrderArrayList() {
        return orderArrayList;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String  getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }

    public static void setOrderArrayList(ArrayList<OrdersManage> orderArrayList) {
        OrdersManage.orderArrayList = orderArrayList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setType(String  type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(String  date) {
        this.date = date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

