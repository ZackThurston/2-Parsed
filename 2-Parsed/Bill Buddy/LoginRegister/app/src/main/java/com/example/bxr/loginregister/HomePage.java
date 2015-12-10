package com.example.bxr.loginregister;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import android.widget.Toast;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    Button bLogout, bBills;
    EditText etFirstName;
    ListView houseMates;
    TextView first_name;
    TextView last_name;
    TextView email;
    TextView tvMyProfile, houseName;
    UserLocalStore userLocalStore;
    ArrayList<User> arrayOfMates = new ArrayList<User>();
    String url = "http://2parsedapp.esy.es/zackFetch.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        houseMates = (ListView) findViewById(R.id.houseMates);
        bLogout = (Button) findViewById(R.id.bLogout);
        bBills = (Button) findViewById(R.id.bBills);
        tvMyProfile = (TextView) findViewById(R.id.tvMyProfile);
        first_name = (TextView) findViewById(R.id.first_name);
        last_name = (TextView) findViewById(R.id.last_name);
        email = (TextView) findViewById(R.id.email);

        houseName = (TextView) findViewById(R.id.houseName);
        etFirstName = (EditText) findViewById(R.id.etFirstName);

        bLogout.setOnClickListener(this);
        bBills.setOnClickListener(this);
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
        User user = userLocalStore.getLoggedInUser();
//        if( !user.house.equals("NULL")) {
//            User otherUser = new User(user.house);
//            ServerRequest serverRequest = new ServerRequest(this);
//            serverRequest.fetchHouseMateDataInBackground(otherUser, new GetUserCallback() {
//                @Override
//                public void done(User returnedUser) {
//                    mate1.setText(returnedUser.first_name +"\n" + returnedUser.email);
//                }
//            });
//        }
        tvMyProfile.setText(user.first_name);
        if( user.house.equals("NULL") ) {
            houseName.setText("You currently don't belong to a house. To join a house, edit your profile and put the house's name that you want to join as the name of your house.");
        }
        else {
            houseName.setText(user.house);
            new BackgroundTask().execute(url);
        }
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

            case R.id.bBills:

                startActivity(new Intent(this, Bills.class));
                break;

            case R.id.tvMyProfile:

                startActivity(new Intent(this, MyProfile.class));
                break;

        }
    }

    public class BackgroundTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String content = HttpULRConnect.getMates(url);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                User user = userLocalStore.getLoggedInUser();

                JSONArray jarray = new JSONArray(s);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject json_data = jarray.getJSONObject(i);
                    if( Integer.parseInt(json_data.getString("user_id")) == (user.user_id))
                        System.out.println("don't want to print out the local user twice");
                    else {
                        User otherUser = new User();
                        otherUser.first_name = json_data.getString("first_name");
                        otherUser.last_name = json_data.getString("last_name");
                        otherUser.email = json_data.getString("email");
                        arrayOfMates.add(otherUser);
                    }
                }
            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data " + e.toString());

            }
            HouseAdapter adapter = new HouseAdapter(HomePage.this, R.layout.house_layout, arrayOfMates);
            ListView houseMates = (ListView) findViewById(R.id.houseMates);
            houseMates.setAdapter(adapter);
        }
    }
}