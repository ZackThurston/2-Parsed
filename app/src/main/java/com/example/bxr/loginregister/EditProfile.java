package com.example.bxr.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfile extends ActionBarActivity implements View.OnClickListener {

    Button doneEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        doneEditing = (Button) findViewById(R.id.doneEditing);

        doneEditing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.doneEditing:

                startActivity(new Intent(this, MyProfile.class));

                break;

        }
    }

}