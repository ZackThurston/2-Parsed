package com.example.bxr.loginregister;

import android.app.AlertDialog;
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

    Button bFinishEdit;
    EditText etFirstName, etLastName, etEmail, etPassword, confirmPassword;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        bFinishEdit = (Button) findViewById(R.id.bFinishEdit);

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


            case R.id.bFinishEdit:

                User user = userLocalStore.getLoggedInUser();

                String first_name = etFirstName.getText().toString();
                String last_name = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String password2 = confirmPassword.getText().toString();
                if( password.equals("")){
                    password = user.password;
                    password2 = user.password;
                }

                if( password.equals(password2) ){

                    User updatedUser = new User(first_name, last_name, email, password, user.user_id, user.house);
                    userLocalStore.storeUserData(updatedUser);

                    editUser(updatedUser);

                    startActivity(new Intent(this, MyProfile.class));
                }else {
                    showErrorMessage();
                }
        }
    }

    private void editUser(User user){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.editUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(MainActivity.this, MyProfile.class));
            }
        });
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setMessage("Passwords did not match");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

}


