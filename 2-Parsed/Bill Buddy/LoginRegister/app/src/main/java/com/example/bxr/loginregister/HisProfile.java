package com.example.bxr.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HisProfile extends ActionBarActivity implements View.OnClickListener {

    Button home;
    TextView firstName, lastName, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_his_profile);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        email = (TextView) findViewById(R.id.email);
        home = (Button) findViewById(R.id.home);

        home.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        displayUserDetails();

    }


    private void displayUserDetails() {
        final User otherUser = new User("bickelbwk@gmail.com", "b");
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.fetchUserDataInBackground(otherUser, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                firstName.setText(returnedUser.first_name);
                lastName.setText(returnedUser.last_name);
                email.setText(returnedUser.email);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:

                startActivity(new Intent(this, HomePage.class));

                break;
        }
    }
}
