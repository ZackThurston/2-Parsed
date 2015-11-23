package com.example.bxr.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity implements View.OnClickListener{

    Button bRegister;
    TextView tvLoginLink;
    EditText etFirstName, etLastName, etEmail, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);
        tvLoginLink = (TextView) findViewById(R.id.tvLoginLink);

        bRegister.setOnClickListener(this);
        tvLoginLink.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bRegister:

                String first_name = etFirstName.getText().toString();
                String last_name = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User(first_name, last_name, email, password);

                registerUser(user);

                break;

            case R.id.tvLoginLink:

                startActivity(new Intent(this, Login.class));
        }
    }

    private void registerUser(User user){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }
}
