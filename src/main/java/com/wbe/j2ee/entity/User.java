package com.wbe.j2ee.entity;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String useremail;
    private String phone;
    private String address1;
    private String address2;
    private String address3;
    /**
     * 状态：0代表未激活，1代表激活
     */
    private Integer status;
    /**
     * 利用UUID生成一段数字，发动到用户邮箱，当用户点击链接时
     * 在做一个校验如果用户传来的code跟我们发生的code一致，更改状态为“1”来激活用户
     */
    private String  code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress1() { return address1; }

    public void setAddress1(String address1) { this.address1 = address1; }

    public String getAddress2() { return address2; }

    public void setAddress2(String address2) { this.address2 = address2; }

    public String getAddress3() { return address3; }

    public void setAddress3(String address3) { this.address3 = address3; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", useremail='" + useremail + '\'' +
                ", phone='" + phone + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", address3='" + address3 + '\'' +
                ", status=" + status +
                ", code='" + code + '\'' +
                '}';
    }
}

