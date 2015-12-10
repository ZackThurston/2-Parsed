package com.example.bxr.loginregister;

/**
 * Created by BXR on 11/5/2015.
 */
public class User {
    String first_name, last_name, email, password, house;
    Integer user_id;

    public User(String first_name, String last_name, String email, String password, Integer user_id, String house){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.user_id = user_id;
        this.house = house;
    }

    public User(String first_name, String last_name, String email, String password, Integer user_id){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.user_id = user_id;
        this.house = "";
    }

    public User(String email, String password){
        this.user_id = 0;
        this.email = email;
        this.password = password;
        this.first_name = "";
        this.last_name = "";
        this.house = "";
    }

    public User(String house){
        this.user_id = 0;
        this.email = "";
        this.password = "";
        this.first_name = "";
        this.last_name = "";
        this.house = house;
    }

    public User(Integer user_id){
        this.user_id = user_id;
        this.email = "";
        this.password = "";
        this.first_name = "";
        this.last_name = "";
        this.house = "";
    }
    public User(){
        this.first_name = "";
        this.last_name = "";
        this.email = "";
        this.password = "";
        this.user_id = 0;
        this.house = "";
    }

    public String getFirst_name(){
        return this.first_name;
    }
    public String getLast_name(){
        return this.last_name;
    }
    public String getEmail(){
        return this.email;
    }

}
