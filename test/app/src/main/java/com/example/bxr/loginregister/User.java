package com.example.bxr.loginregister;

/**
 * Created by BXR on 11/5/2015.
 */
public class User {
    String first_name, last_name, email, password;

    public User(String first_name, String last_name, String email, String password){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
        this.first_name = "";
        this.last_name = "";
    }
}
