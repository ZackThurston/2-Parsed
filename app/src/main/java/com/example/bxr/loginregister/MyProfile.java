package com.example.bxr.loginregister;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class MyProfile extends ActionBarActivity implements View.OnClickListener {

    Button editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        editProfile = (Button) findViewById(R.id.editProfile);

        editProfile.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editProfile:

                startActivity(new Intent(this, EditProfile.class));

                break;
        }
    }
}
