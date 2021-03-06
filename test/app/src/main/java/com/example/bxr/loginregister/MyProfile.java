package com.example.bxr.loginregister;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {

    Button editProfile, homePage;
    TextView name, lastName, email, user_id;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        editProfile = (Button) findViewById(R.id.editProfile);
        homePage = (Button) findViewById(R.id.homePage);
        name = (TextView) findViewById(R.id.name);
        lastName = (TextView) findViewById(R.id.lastName);
        email = (TextView) findViewById(R.id.email);
        user_id = (TextView) findViewById(R.id.user_id);



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
        user_id.setText("" + user.user_id);
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
