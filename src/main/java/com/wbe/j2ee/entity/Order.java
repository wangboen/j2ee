package com.wbe.j2ee.entity;

public class Order {
    private String id;
    private int orderid;
    private int userid;
    private String restaurantuuid;
    private String name;
    private int number;
    private Float cost;
    private Float subtotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getRestaurantuuid() {
        return restaurantuuid;
    }

    public void setRestaurantuuid(String restaurantuuid) {
        this.restaurantuuid = restaurantuuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }
}
