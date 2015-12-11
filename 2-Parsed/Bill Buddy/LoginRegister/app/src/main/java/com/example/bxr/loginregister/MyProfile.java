package com.example.bxr.loginregister;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {

    Button editProfile, homePage;
    TextView name, lastName, email, house;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        editProfile = (Button) findViewById(R.id.editProfile);
        homePage = (Button) findViewById(R.id.homePage);
        name = (TextView) findViewById(R.id.name);
        lastName = (TextView) findViewById(R.id.lastName);
        email = (TextView) findViewById(R.id.email);
        house = (TextView) findViewById(R.id.house);



        editProfile.setOnClickListener(this);
        homePage.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

            displayUserDetails();

    }


    private void displayUserDetails() {
        User user = userLocalStore.getLoggedInUser();

        name.setText(user.first_name);
        lastName.setText(user.last_name);
        if( !user.house.equals( "NULL"))
            house.setText(user.house);
        else
            house.setText("-");
        email.setText(user.email);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editProfile:

                startActivity(new Intent(this, MainActivity.class));

                break;
            case R.id.homePage:

                startActivity(new Intent(this, HomePage.class));

                break;
        }
    }
}
