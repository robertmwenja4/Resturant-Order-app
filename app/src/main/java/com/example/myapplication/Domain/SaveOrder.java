package com.example.myapplication.Domain;

public class SaveOrder {

    private String titles;
    private String cost;
    private String time;
    private String orderID;

    public SaveOrder() {
    }

    public SaveOrder(String titles, String cost, String time, String orderID) {
        this.titles = titles;
        this.cost = cost;
        this.time = time;
        this.orderID = orderID;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
