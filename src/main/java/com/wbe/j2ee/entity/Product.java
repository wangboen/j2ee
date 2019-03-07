package com.wbe.j2ee.entity;

import java.util.Date;

public class Product {
    private int id;
    private String restaurantUUID;
    private String name;
    private String type;
    private int number;
    private Float cost;
    private Date date;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getRestaurantUUID() { return restaurantUUID; }

    public void setRestaurantUUID(String restaurantUUID) { this.restaurantUUID = restaurantUUID; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Float getCost() { return cost; }

    public void setCost(Float cost) { this.cost = cost; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }
}
