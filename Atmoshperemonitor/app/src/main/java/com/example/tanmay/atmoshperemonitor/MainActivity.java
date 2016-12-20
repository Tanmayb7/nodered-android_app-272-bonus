package com.example.tanmay.atmoshperemonitor;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity{

    public static String global_ans = "";
    public static MainActivity myclass = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myclass = this;

        final MyHTTPConnector test;
        Context context = getApplicationContext();
        CharSequence text = null;
        //Log.e("construction"," Constructing myhttp connector" );

        Timer timer = new Timer();


        TimerTask task = new TimerTask()
        {
            String result = "will be populated";
            @Override
            public void run()
            {
                //Log.e("Timertask","Entering timertask");
                try {
                    global_ans = result = new MyHTTPConnector().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                myclass.toastAns();
//                Notificator notificator = new Notificator(global_ans);

                //Log.e("Toast","Making a toast");
            }
        };
        timer.schedule(task, 0, 3000);
        //	timer.cancel();
    }
    public void toastAns()
    {
        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {
            public void run() {

                int tempCheck = 0, co2Check = 0, noiseCheck = 0;
                String temperature = global_ans.substring(13,15);
                String colevel = global_ans.substring(28,31);
                String noiselevel = global_ans.substring(46,48);

                //Log.e("global_ans",global_ans);
                //Log.e("temperature","Temp is  : "  + temperature);
                //Log.e("colevel","co level is  : " + colevel);
                TextView textViewToChange = (TextView) findViewById(R.id.temp);
                textViewToChange.setText(temperature);
                TextView textViewToChange2 = (TextView) findViewById(R.id.co);
                textViewToChange2.setText(colevel);
                TextView textViewToChange3 = (TextView) findViewById(R.id.noise);
                textViewToChange3.setText(noiselevel);

                try {
                    tempCheck = Integer.parseInt(temperature);
                    co2Check = Integer.parseInt(colevel);
                    noiseCheck = Integer.parseInt(noiselevel);
                }catch(Exception e)
                {
                    //Log.e("Parsing problem","Received corrupt data");
                    e.printStackTrace();
                }

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());

                if(tempCheck > 70) {
                    mBuilder.setSmallIcon(R.drawable.ic_stat_name);
                    mBuilder.setContentTitle("Atmoshperic alert.!");
                    mBuilder.setContentText("" + tempCheck + " F - Temperature is very high.!");
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, mBuilder.build());

                    //Log.e("Toasting","I like toasted results");
                    Context context = getApplicationContext();
                    CharSequence text = "Aren't you feeling HOT!! It's " + tempCheck +" F";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                if(co2Check > 400) {
                    mBuilder.setSmallIcon(R.drawable.ic_stat_name);
                    mBuilder.setContentTitle("Atmoshperic alert.!");
                    mBuilder.setContentText("" + co2Check +" ppm - High Level of CO2 is detected.!");
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, mBuilder.build());

                    //Log.e("Toasting","I like toasted results");
                    Context context = getApplicationContext();
                    CharSequence text = "Get some air.! Open windows. CO2 level is high It's " + co2Check +" PPM";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                if(noiseCheck > 95) {
                    mBuilder.setSmallIcon(R.drawable.ic_stat_name);
                    mBuilder.setContentTitle("Atmoshperic alert.!");
                    mBuilder.setContentText("" + noiseCheck +" dB - High level of Noise is detected.!");
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, mBuilder.build());

                    //Log.e("Toasting","I like toasted results");
                    Context context = getApplicationContext();
                    CharSequence text = "It's too Noisy no.! It's " + co2Check +" PPM";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

// notificationID allows you to update the notification later on.
            }
        });

    }
}

