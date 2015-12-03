package com.example.bxr.loginregister;

/**
 * Created by Glon on 12/1/2015.
 */
import android.content.Context;
import android.content.SharedPreferences;

public class BillLocalStore {
    public static final String SP_NAME = "billDetails";
    SharedPreferences billLocalDatabase;

    public BillLocalStore(Context context) {
        billLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeBillData(Bill bill){
        SharedPreferences.Editor spEditor = billLocalDatabase.edit();
        spEditor.putString("bill_name", bill.bill_name);
        spEditor.putString("bill_ammount", bill.bill_amount);
        spEditor.putString("bill_date", bill.bill_date);
        spEditor.putString("bill_into", bill.bill_info);
        spEditor.commit();
    }

    public Bill getBill(){
        String bill_name = billLocalDatabase.getString("bill_name", "");
        String bill_amount = billLocalDatabase.getString("bill_amount", "");
        String bill_date = billLocalDatabase.getString("bill_date", "");
        String bill_info = billLocalDatabase.getString("bill_info", "");


        Bill storedBill = new Bill(bill_name, bill_amount, bill_date, bill_info);
        return storedBill;
    }

    public void clearBillData(){
        SharedPreferences.Editor spEditor = billLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}

