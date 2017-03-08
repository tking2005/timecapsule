package com.timecapsule.app.profilefragment.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.internal.zzbmn;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.List;

/**
 * Created by catwong on 3/7/17.
 */
public class User {

    public String name;
    public String username;
    public String email;
    public String userId;





    public User(){}


    public User(String email){
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

    public void setEmail(String email) {
        this.email = email;
    }
}
