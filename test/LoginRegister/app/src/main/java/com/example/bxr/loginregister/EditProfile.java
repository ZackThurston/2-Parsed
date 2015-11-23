package com.example.bxr.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    Button doneEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        doneEditing = (Button) findViewById(R.id.doneEditing);

        doneEditing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.doneEditing:

                startActivity(new Intent(this, HomePage.class));

                break;

        }
    }

    private void editUser(User user){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.editUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(EditProfile.this, HomePage.class));
            }
        });
    }

}