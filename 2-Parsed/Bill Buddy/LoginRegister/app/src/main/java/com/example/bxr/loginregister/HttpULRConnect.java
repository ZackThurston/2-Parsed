package com.example.bxr.loginregister;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Glon on 12/8/2015.
 */

public class HttpULRConnect {
    public static String getData(String uri){

        BufferedReader reader = null;
        try {

            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Log.d("testhtt2", "test");
            String line;
            while ((line= reader.readLine())!=null) {

                sb.append(line+"\n");


            }
            Log.d("test44", sb.toString());
            return sb.toString();


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        finally{

            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return null;
                }

            }
        }
    }

    public static String getMates(String uri){

        BufferedReader reader = null;
        try {

            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line= reader.readLine())!=null) {

                sb.append(line+"\n");


            }
            Log.d("house test", sb.toString());
            return sb.toString();


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        finally{

            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return null;
                }

            }
        }
    }
}

