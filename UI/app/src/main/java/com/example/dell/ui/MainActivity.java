package com.example.dell.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.net.Proxy.Type.HTTP;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final int READ_TIMEOUT = 1500000;
 private adapter mAdapter;
 private List<DataFish> data;
    JsonParser jsonParser=new JsonParser();
    JSONObject jobj = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("News");

        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        data=new ArrayList<>();
    new AsyncFetch().execute();
            //  loadUrl();
    }
    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
@Override
        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
             url=new URL("http://sih2020-cryptx.herokuapp.com/news");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;}
//        @Override
//        protected String doInBackground(String... params) {


//            try {
//
//                // Enter URL address where your json file resides
//                // Even you can make call to php file which returns json data
//                url = new URL("http://sih2020-cryptx.herokuapp.com/news");
//
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return e.toString();
//            }
//            try {
//
//                // Setup HttpURLConnection class to send and receive data from php and mysql
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(READ_TIMEOUT);
//                conn.setConnectTimeout(CONNECTION_TIMEOUT);
//                conn.setRequestMethod("GET");
//
//                // setDoOutput to true as we recieve data from json file
//                conn.setDoOutput(true);
//
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//                return e1.toString();
//            }

//            try {
//
//
//                int response_code = conn.getResponseCode();
//
//                // Check if successful connection made
//                if (response_code == HttpURLConnection.HTTP_OK) {
//
//                    // Read data sent from server
//                    InputStream input = conn.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(input,"utf-8"), 8);
//                    StringBuilder result = new StringBuilder();
//                    String line;
//
//                    while ((line = reader.readLine()) != null) {
//                        result.append(line);
//                    }
//
//                    // Pass data to onPostExecute method
//
//                    return (result.toString());
//
//                }
//
//
////                else {
////                  //  Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_LONG).show();
////                    return ("UNSUCCESSFUL");
////                }
//                      return "some statement";
//            } catch (IOException e) {
//                e.printStackTrace();
//                return e.toString();
//            } finally {
//                conn.disconnect();
//            }
//
//
//        }

        @Override
        protected void onPostExecute(String s) {
             pdLoading.dismiss();
            List<DataFish> data=new ArrayList<>();
            pdLoading.dismiss();

        try{

            JSONArray jArray = new JSONArray(s);
    //        Log.w("err",jArray)



            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);


                DataFish fishData = new DataFish();
                fishData.title=json_data.getString("title");
                fishData.url=json_data.getString("url");
                fishData.date=json_data.getString("publishedAt");
                fishData.author=json_data.getString("author");
                fishData.content=json_data.getString("content");
                   Log.w("error",fishData.title);
                   data.add(fishData);
            }
            recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
            mAdapter = new adapter(data,MainActivity.this);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }}}
//    private void loadUrl(){
//        final TextView t = findViewById(R.id.txt);
//        RequestQueue queue = Volley.newRequestQueue(this);
//        final List<DataFish> data=new ArrayList<>();
//
//
//        String url ="http://sih2020-cryptx.herokuapp.com/news";
//
//
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null,
//                new Response.Listener<JSONObject>(){
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//
//
//                            JSONArray array = response.getJSONArray("items");
//
//
//
//                            for(int i=0;i<array.length();i++){
//                                JSONObject json_data = array.getJSONObject(i);
//
//
//                                DataFish fishData = new DataFish();
//                                fishData.title=json_data.getString("title");
//                                fishData.url=json_data.getString("url");
//                                fishData.date=json_data.getString("publishedAt");
//                                fishData.author=json_data.getString("author");
//                                fishData.content=json_data.getString("content");
//                                //Log.w("error",fishData.title);
//                            data.add()
//                            }
//
//                            recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
//                            mAdapter=new adapter(data,MainActivity.this);
//                            recyclerView.setAdapter(mAdapter);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//
//
//                        } catch (JSONException e) {
//
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(MainActivity.this, "Errorjjbkehihaofhoewhsfcoewdo" + error.toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//
//    }
//}
