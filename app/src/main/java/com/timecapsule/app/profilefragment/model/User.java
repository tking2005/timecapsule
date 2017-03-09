package com.timecapsule.app.profilefragment.model;

/**
 * Created by catwong on 3/7/17.
 */
public class User {

    public String name;
    public String username;
    public String email;
    public String userId;

    public User() {
    }


    public User(String email) {
        this.email = email;

    }


    public User(String name, String username, String email, String userId) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
