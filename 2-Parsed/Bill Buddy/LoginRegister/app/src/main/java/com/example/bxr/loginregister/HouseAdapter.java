package com.example.bxr.loginregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by student on 12/10/15.
 */

public class HouseAdapter extends ArrayAdapter<User> {
    private ArrayList<User> items;
    private Context mContext;

    public HouseAdapter(Context context, int textViewResourceID, ArrayList<User> items) {
        super(context, textViewResourceID, items);
        mContext = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        User user = items.get(position);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.house_layout, null);

        }
        TextView first_name = (TextView) v.findViewById(R.id.first_name);
//        TextView last_name = (TextView) v.findViewById(R.id.last_name);
        TextView email = (TextView) v.findViewById(R.id.email);

        if (first_name != null) {
            first_name.setText(user.getFirst_name());
        }
//        if (last_name != null) {
//            last_name.setText(user.getLast_name());
//        }
        if (email != null) {
            email.setText(user.getEmail());
        }


        return v;
    }
}