package com.firstapp.kyecothreads;

public class userdata {
    String email, password, phone, name;
    String points, water, cotton;

    public userdata() {
    }

    public userdata(String email, String password, String phone, String name, String water, String cotton, String points) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.water = water;
        this.cotton = cotton;
        this.points = points;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getCotton() {
        return cotton;
    }

    public void setCotton(String cotton) {
        this.cotton = cotton;
    }


}
