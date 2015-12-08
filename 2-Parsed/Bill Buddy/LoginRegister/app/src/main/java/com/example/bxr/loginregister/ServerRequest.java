package com.example.bxr.loginregister;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BXR on 11/8/2015.
 */
public class ServerRequest {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;

    public ServerRequest(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait..");
    }

    public void storeUserDataInBackground(User user, GetUserCallback callBack) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, callBack).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallback callBack) {
        progressDialog.show();
        new FetchUserDataAsyncTask(user,callBack).execute();
    }

    public void fetchHouseMateDataInBackground(User user, GetUserCallback callBack) {
        progressDialog.show();
        new FetchHouseMateDataAsyncTask(user,callBack).execute();
    }

    public void editUserDataInBackground(User user, GetUserCallback callBack) {
        progressDialog.show();
        new EditUserDataAsyncTask(user,callBack).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

        User user;
        GetUserCallback userCallBack;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallBack.done(null);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http://2parsedapp.esy.es/Register.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("first_name", user.first_name)
                        .appendQueryParameter("last_name", user.last_name)
                        .appendQueryParameter("email", user.email)
                        .appendQueryParameter("password", user.password);
                String query = builder.build().getEncodedQuery();
                Log.d("query", query);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                int code = conn.getResponseCode();
                Log.d("code", Integer.toString(code));
                conn.connect();
            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {

        User user;
        GetUserCallback userCallBack;

        public FetchUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            HashMap<String, String> param = new HashMap<String, String>();
            String requestURL = "http://2parsedapp.esy.es/FetchUserData.php";
            param.put("email", user.email);
            param.put("password", user.password);

            URL url;
            User returnedUser = null;
            try {
                url = new URL(requestURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(param));

                writer.flush();
                writer.close();
                os.close();
                int code = conn.getResponseCode();
                Log.d("code", Integer.toString(code));

                InputStream responseStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = responseStreamReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                responseStreamReader.close();

                String response = stringBuilder.toString();
                Log.d("response",response);
                JSONObject jsonResponse = new JSONObject(response);
                Log.d("length",Integer.toString(jsonResponse.length()));
                if(jsonResponse.length() == 0){
                    Log.d("No JSON response","No JSON response");
                    returnedUser = null;
                }else{
                    String name = jsonResponse.getString("first_name");
                    String last_name = jsonResponse.getString("last_name");
                    Integer user_id = jsonResponse.getInt("user_id");
                    String house = jsonResponse.getString("house");
                    String email = jsonResponse.getString("email");
                    String password = jsonResponse.getString("password");
                    returnedUser = new User(name, last_name, email, password, user_id,house);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }

        private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallBack.done(returnedUser);
            //Log.d("post execute",returnedUser.email);
            super.onPostExecute(returnedUser);
        }

    }

    public class EditUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

        User user;
        GetUserCallback userCallBack;

        public EditUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallBack.done(null);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http://2parsedapp.esy.es/EditUserData.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("first_name", user.first_name)
                        .appendQueryParameter("last_name", user.last_name)
                        .appendQueryParameter("password", user.password)
                        .appendQueryParameter("user_id", ""+user.user_id);
                String query = builder.build().getEncodedQuery();
                Log.d("query", query);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                int code = conn.getResponseCode();
                Log.d("code", Integer.toString(code));
                conn.connect();
            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public class FetchHouseMateDataAsyncTask extends AsyncTask<Void, Void, User> {

        User user;
        GetUserCallback userCallBack;

        public FetchHouseMateDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            HashMap<String, String> param = new HashMap<String, String>();
            String requestURL = "http://2parsedapp.esy.es/FetchHousemate.php";
            param.put("house", user.house);

            URL url;
            User returnedUser = null;
            try {
                url = new URL(requestURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(param));

                writer.flush();
                writer.close();
                os.close();
                int code = conn.getResponseCode();
                Log.d("code", Integer.toString(code));

                InputStream responseStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = responseStreamReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                responseStreamReader.close();

                String response = stringBuilder.toString();
                Log.d("response",response);
                JSONObject jsonResponse = new JSONObject(response);
                Log.d("length",Integer.toString(jsonResponse.length()));
                if(jsonResponse.length() == 0){
                    Log.d("No JSON response","No JSON response");
                    returnedUser = null;
                }else{

                    String name = jsonResponse.getString("first_name");
                    String email = jsonResponse.getString("email");
                    String password = jsonResponse.getString("password");
                    String last_name = jsonResponse.getString("last_name");
                    Integer user_id = jsonResponse.getInt("user_id");
                    String house = jsonResponse.getString("house");
                    returnedUser = new User(name, last_name, email, password, user_id, house);
                    Log.d("returned user's house",returnedUser.house);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }

        private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            System.out.println(returnedUser);
            userCallBack.done(returnedUser);
            Log.d("post execute", returnedUser.email);
            super.onPostExecute(returnedUser);
        }

    }

    //Bill information starts here
    public void storeBillDataInBackground(Bill bill, GetBillCallback callBack) {
        progressDialog.show();
        new StoreBillDataAsyncTask(bill, callBack).execute();
    }

    public void fetchBillDataInBackground(Bill bill, GetBillCallback callBack) {
        progressDialog.show();
        new FetchBillDataAsyncTask(bill,callBack).execute();
    }

    public class StoreBillDataAsyncTask extends AsyncTask<Void, Void, Void> {

        Bill bill;
        GetBillCallback billCallBack;

        public StoreBillDataAsyncTask(Bill bill, GetBillCallback billCallBack) {
            this.bill = bill;
            this.billCallBack = billCallBack;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            billCallBack.done(null);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http://2parsedapp.esy.es/Bill.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("bill_name", bill.bill_name)
                        .appendQueryParameter("bill_amount", bill.bill_amount)
                        .appendQueryParameter("bill_date", bill.bill_date)
                        .appendQueryParameter("bill_info", bill.bill_info);
                String query = builder.build().getEncodedQuery();
                Log.d("query", query);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                int code = conn.getResponseCode();
                Log.d("code", Integer.toString(code));
                conn.connect();
            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public class FetchBillDataAsyncTask extends AsyncTask<Void, Void, Bill> {

        Bill bill;
        GetBillCallback billCallBack;

        public FetchBillDataAsyncTask(Bill bill, GetBillCallback billCallBack) {
            this.bill = bill;
            this.billCallBack = billCallBack;
        }

        @Override
        protected Bill doInBackground(Void... params) {
            HashMap<String, String> param = new HashMap<String, String>();
            String requestURL = "http://2parsedapp.esy.es/FetchBillData.php";
            //param.put("email", user.email);
            //param.put("password", user.password);
            param.put("bill", bill.bill_name);

            URL url;
            Bill returnedBill = null;
            try {
                url = new URL(requestURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(param));

                writer.flush();
                writer.close();
                os.close();
                int code = conn.getResponseCode();
                Log.d("code", Integer.toString(code));

                InputStream responseStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = responseStreamReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                responseStreamReader.close();

                String response = stringBuilder.toString();
                Log.d("response",response);
                JSONObject jsonResponse = new JSONObject(response);
                Log.d("length",Integer.toString(jsonResponse.length()));
                if(jsonResponse.length() == 0){
                    returnedBill = null;
                }else{
                    String name = jsonResponse.getString("bill_name");
                    String amount = jsonResponse.getString("bill_amount");
                    String date = jsonResponse.getString("bill_date");
                    String info = jsonResponse.getString("bill_info");
                    returnedBill = new Bill(name, amount, date, info);
                    Log.d("returned bill", returnedBill.bill_name);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedBill;
        }

        private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(Bill returnedBill) {
            progressDialog.dismiss();
            billCallBack.done(returnedBill);
            Log.d("post execute", returnedBill.bill_name);
            super.onPostExecute(returnedBill);
        }

    }

}