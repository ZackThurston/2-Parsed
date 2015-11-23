package com.example.bxr.loginregister;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLogout, bEditProfile, bFinishEdit;
    EditText etFirstName, etLastName, etEmail, etPassword;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bLogout = (Button) findViewById(R.id.bLogout);
        bEditProfile = (Button) findViewById(R.id.bEditProfile);
        bFinishEdit = (Button) findViewById(R.id.bFinishEdit);

        bLogout.setOnClickListener(this);
        bEditProfile.setOnClickListener(this);
        bFinishEdit.setOnClickListener(this);

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

        etFirstName.setText(user.first_name);
        etLastName.setText(user.last_name);
        etEmail.setText(user.email);

    }
    private boolean authenticate() {
        return userLocalStore.getUserLoggedIn();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogout:

                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);


                startActivity(new Intent(this, Login.class));

                break;

            case R.id.bEditProfile:

                startActivity(new Intent(this, EditProfile.class));

                break;

            case R.id.bFinishEdit:

                String first_name = etFirstName.getText().toString();
                String last_name = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User(first_name, last_name, email, password);

                editUser(user);

                startActivity(new Intent(this, HomePage.class));

        }
    }

    private void editUser(User user){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.editUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(MainActivity.this, HomePage.class));
            }
        });
    }

}


