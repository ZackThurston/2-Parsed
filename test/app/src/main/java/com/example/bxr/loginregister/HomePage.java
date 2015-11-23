package com.example.bxr.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    Button bLogout;
    EditText etFirstName;
    TextView tvMyProfile;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bLogout = (Button) findViewById(R.id.bLogout);
        tvMyProfile = (TextView) findViewById(R.id.tvMyProfile);
        etFirstName = (EditText) findViewById(R.id.etFirstName);

        bLogout.setOnClickListener(this);
        tvMyProfile.setOnClickListener(this);

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
        //User user = userLocalStore.getLoggedInUser();

        //etFirstName.setText(user.first_name);
        //etLastName.setText(user.last_name);
        //etEmail.setText(user.email);

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

        }
    }
}