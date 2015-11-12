package com.example.bxr.loginregister;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by BXR on 11/5/2015.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);


    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("first_name", user.first_name);
        spEditor.putString("last_name", user.last_name);
        spEditor.putString("email", user.email);
        spEditor.putString("password", user.password);
        spEditor.commit();
    }

    public User getLoggedInUser(){
        String first_name = userLocalDatabase.getString("first_name", "");
        String last_name = userLocalDatabase.getString("last_name", "");
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password","" );

        User storedUser = new User(first_name, last_name, email, password);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if (userLocalDatabase.getBoolean("loggedIn", false) == true){
            return true;
        }
        else{
            return false;
        }
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
