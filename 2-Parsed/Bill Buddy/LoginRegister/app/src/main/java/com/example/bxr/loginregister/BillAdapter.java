package com.example.bxr.loginregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Glon on 12/8/2015.
 */
public class BillAdapter extends ArrayAdapter<Bill> {
    private ArrayList<Bill> items;
    private Context mContext;

    public BillAdapter(Context context, int textViewResourceID, ArrayList<Bill> items) {
        super(context, textViewResourceID, items);
        mContext = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        Bill bill = items.get(position);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.bill_layout, null);

        }
        TextView bill_name = (TextView) v.findViewById(R.id.bill_name);
        TextView bill_amount = (TextView) v.findViewById(R.id.bill_amount);
        TextView bill_date = (TextView) v.findViewById(R.id.bill_date);
        TextView bill_info = (TextView) v.findViewById(R.id.bill_info);

        if (bill_name != null) {
            bill_name.setText(bill.getBill_name());
        }
        if (bill_amount != null) {
            bill_amount.setText(bill.getBill_amount());
        }
        if (bill_date != null) {
            bill_date.setText(bill.getBill_date());
        }
        if (bill_info != null) {
            bill_info.setText(bill.getBill_info());
        }

        return v;
    }
}
