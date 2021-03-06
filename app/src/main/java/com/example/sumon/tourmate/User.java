package com.example.sumon.tourmate;

public class User {

    private String id;
    private String name;
    private String mobile;
    private String email;

    public User() {
        //required for firebase
    }

    public User(String id,String name, String mobile, String email) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }
}
