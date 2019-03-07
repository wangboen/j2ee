package com.wbe.j2ee.entity;

public class Restaurant {
    private Integer id;
    private String UUID;
    private String name;
    private String address;
    private String type;
    private Integer status = 0;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUUID() { return UUID; }

    public void setUUID(String UUID) { this.UUID = UUID; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", UUID='" + UUID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                '}';
    }
}
