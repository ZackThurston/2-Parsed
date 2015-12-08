package com.example.bxr.loginregister;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class Bills extends AppCompatActivity implements View.OnClickListener {

    //Arrays of options --> ArrayAdapter --> ListView
    //List view: {views: bill_list.xml}


    //EditText;
    ListView billList;
    Button bNewBill, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        billList = (ListView) findViewById(R.id.billList);

        populateListView();
        //registerClickCallback();

        bNewBill  = (Button) findViewById(R.id.bNewBill);
        bNewBill.setOnClickListener(this);

        home  = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);
    }


    private void populateListView() {
        //Create list of items
        String[] billArray = {"Water", "Electric", "Rent", "Groceries", "Electric", "Rent", "Groceries", "Electric", "Rent", "Groceries", "Electric", "Rent", "Groceries", "Electric", "Rent", "Groceries"};

        //Build Adapter
        ArrayAdapter<String> billAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, billArray);

        //Configure the list view
        billList.setAdapter(billAdapter);
    }


    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.billList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textview = (TextView) viewClicked;
                String Message = "You Clicked #" + position;
                Toast.makeText(Bills.this, Message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bNewBill:
                startActivity( new Intent(this, CreateBill.class));
                break;

            case R.id.home:
                startActivity( new Intent(this, HomePage.class));
                break;
        }

    }

}