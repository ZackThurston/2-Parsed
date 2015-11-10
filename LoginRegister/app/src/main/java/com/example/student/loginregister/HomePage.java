package com.example.student.loginregister;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class HomePage extends ActionBarActivity implements View.OnClickListener {

    Button myProfile, hisProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        myProfile = (Button) findViewById(R.id.myProfile);
        hisProfile = (Button) findViewById(R.id.hisProfile);

        myProfile.setOnClickListener(this);
        hisProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.myProfile:

                startActivity(new Intent(this, MyProfile.class));

                break;
            case R.id.hisProfile:

                startActivity(new Intent(this, HisProfile.class));

                break;
        }
    }
}
