package com.example.bxr.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateBill extends AppCompatActivity implements View.OnClickListener{

    Button bSubmit, bClear;
    EditText etBillName, etBillAmount, etDate, etBillInfo;
    BillLocalStore billLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);

        //Edit text for creating bill
        etBillName = (EditText) findViewById(R.id.etBillName);
        etBillAmount = (EditText) findViewById(R.id.etBillAmount);
        etDate = (EditText) findViewById(R.id.etDate);
        etBillInfo = (EditText) findViewById(R.id. etBillInfo);
        billLocalStore = new BillLocalStore(this);

        //Buttons for creating bill
        bSubmit = (Button) findViewById(R.id.bSubmit);
        bClear = (Button) findViewById(R.id.bClear);

        //Listeners
        bSubmit.setOnClickListener(this);
        bClear.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bSubmit:

                String bill_name = etBillName.getText().toString();
                String bill_amount = etBillAmount.getText().toString();
                String bill_date = etDate.getText().toString();
                String bill_info = etBillInfo.getText().toString();

                Bill bill = new Bill(bill_name, bill_amount, bill_date, bill_info);
                billLocalStore.storeBillData(bill);
                submitBill(bill);


                break;
            case R.id.bClear:
                break;
        }
    }

    private void submitBill(Bill bill){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.storeBillDataInBackground(bill, new GetBillCallback(){
            @Override
            public void done (Bill returnedBill){
                startActivity(new Intent( CreateBill.this, Bills.class));
            }
        });
    }
}
