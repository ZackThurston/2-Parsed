package com.example.bxr.loginregister;

/**
 * Created by Glon on 12/1/2015.
 */

public class Bill {
    String bill_name, bill_amount, bill_date, bill_info;

    public Bill(String bill_name, String bill_amount, String bill_date, String bill_info){
        this.bill_name = bill_name;
        this.bill_amount = bill_amount;
        this.bill_date = bill_date;
        this.bill_info = bill_info;
    }

    public Bill(){
        this.bill_name = "";
        this.bill_amount = "";
        this.bill_date = "";
        this.bill_info = "";
    }

    public String getBill_name(){
        return this.bill_name;
    }
    public String getBill_amount(){
        return this.bill_amount;
    }
    public String getBill_date(){
        return this.bill_date;
    }
    public String getBill_info(){
        return this.bill_info;
    }
}
