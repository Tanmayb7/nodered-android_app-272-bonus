package com.example.tanmay.atmoshperemonitor;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tanmay on 12/14/2016.
 */

public class MyHTTPConnector extends AsyncTask<Void, Void,String>{

    public MyHTTPConnector() {

    }

//
    protected String doInBackground(Void...Params)
    {
            String ans = "";
            //Log.e("BG process", " running get service");
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
            .url("https://airqualitymonitoring.mybluemix.net/showdata")//"https://airqualitymonitoring.mybluemix.net/showdata")
            .build();
            try {

             Response response = client.newCall(request).execute();
             ans = response.body().string();
            // Log.e("Answers inside my class", ans);

           //  Log.e("Mylog", ans);
            } catch (Exception e) {
                e.printStackTrace();
                //Log.e("Locha", " ho gaya.!!");
            }

            return ans;
    }
}
