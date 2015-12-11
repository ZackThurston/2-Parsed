package com.example.bxr.loginregister;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Bills extends AppCompatActivity implements View.OnClickListener {

    //Arrays of options --> ArrayAdapter --> ListView
    //List view: {views: bill_list.xml}


    //EditText;
    ListView billList;
    TextView bill_name;
    TextView bill_amount;
    TextView bill_date;
    TextView bill_info;
    Button bNewBill, home;
    String url = "http://2parsedapp.esy.es/FetchBillData.php";
    ArrayList<Bill> arrayOfBills = new ArrayList<Bill>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        billList = (ListView) findViewById(R.id.billList);
        bill_name = (TextView) findViewById(R.id.bill_name);
        bill_amount = (TextView) findViewById(R.id.bill_amount);
        bill_date = (TextView) findViewById(R.id.bill_date);
        bill_info = (TextView) findViewById(R.id.bill_info);

        //populateListView();
        //registerClickCallback();

        bNewBill = (Button) findViewById(R.id.bNewBill);
        bNewBill.setOnClickListener(this);

        home  = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);

        new BackTask().execute(url);
    }


    private void populateListView() {
        Bill bill = new Bill();
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.fetchBillDataInBackground(bill, new GetBillCallback() {
            @Override
            public void done(Bill returnedBill) {
                bill_name.setText(returnedBill.bill_name);
            }
        });
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

    public class BackTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String content = HttpULRConnect.getData(url);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jarray = new JSONArray(s);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject json_data = jarray.getJSONObject(i);
                        Bill resultRow = new Bill();
                        resultRow.bill_name = json_data.getString("bill_name");
                        resultRow.bill_amount = json_data.getString("bill_amount");
                        resultRow.bill_date = json_data.getString("bill_date");
                        resultRow.bill_info = json_data.getString("bill_info");
                        arrayOfBills.add(resultRow);
                }
            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data " + e.toString());

            }
            BillAdapter adapter = new BillAdapter(Bills.this, R.layout.bill_layout, arrayOfBills);
            ListView billList = (ListView) findViewById(R.id.billList);
            billList.setAdapter(adapter);
        }
    }
}