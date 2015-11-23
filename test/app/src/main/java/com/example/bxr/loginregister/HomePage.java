package com.example.bxr.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    Button bLogout;
    EditText etFirstName;
    TextView tvMyProfile, tvHisProfile, houseName;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bLogout = (Button) findViewById(R.id.bLogout);
        tvMyProfile = (TextView) findViewById(R.id.tvMyProfile);
        tvHisProfile = (TextView) findViewById(R.id.tvHisProfile);
        houseName = (TextView) findViewById(R.id.houseName);
        etFirstName = (EditText) findViewById(R.id.etFirstName);

        bLogout.setOnClickListener(this);
        tvMyProfile.setOnClickListener(this);
        tvHisProfile.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);

    }

    // This will display the user details if the user is logged in
    @Override
    protected void onStart() {
        super.onStart();

        //If statement will check if the user has logged in and has been authenticated
        if (authenticate()){
            displayUserDetails();
        }else{
            startActivity(new Intent(this, Login.class));
        }

    }

    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();
        User otherUser = new User("test");
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.fetchHouseMateDataInBackground(otherUser, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                tvHisProfile.setText(returnedUser.first_name);
            }
        });

        tvMyProfile.setText(user.first_name);
        System.out.println("house for home page" + user.house);
        houseName.setText(user.house);
//        etLastName.setText(user.last_name);
//        etEmail.setText(user.email);

    }

    private boolean authenticate() {
        return userLocalStore.getUserLoggedIn();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bLogout:

                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(this, Login.class));
                break;

            case R.id.tvMyProfile:

                startActivity(new Intent(this, MyProfile.class));
                break;

            case R.id.tvHisProfile:

                startActivity(new Intent(this, HisProfile.class));
                break;

        }
    }
}