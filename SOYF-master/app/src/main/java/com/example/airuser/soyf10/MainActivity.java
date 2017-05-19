package com.example.airuser.soyf10;

import android.net.Uri;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.*;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    private TextView textView3;//Distance
    private TextView textView4;//Calorie
    private ShareButton fbShare;
    private Profile profile;
    int steperino = 0;
    public boolean fbLogged;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent fromLogin = getIntent();

        fbLogged = false;
        fbLogged = getIntent().getExtras().getBoolean("fb");
        if (fbLogged) {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            profile = Profile.getCurrentProfile();
        }


        Bundle parameters = new Bundle();

        Intent intent = new Intent(this, StepCounterService.class);
        startService(intent);
        SharedPreferences settings = getSharedPreferences("Pref_data", 0);
        editor = settings.edit();


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToSettingsActivity(getFbLogged());
            }
        });

        /*
        //function to determine the distance run in kilometers using average step length for men and number of steps
        public float getDistanceRun(long steps){
            float distance = (float)(steps*78)/(float)100000;
            return distance;
        }
        */

        int total = settings.getInt("totalSteps", 0);
        int daily = settings.getInt("dailySteps", 0);
        textView = (TextView) findViewById(R.id.textView);

        fbShare = (ShareButton) findViewById(R.id.fbShare);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://cecs492.weebly.com/"))
                .setContentDescription("Step On Your Friends")
                .setContentTitle("I've take a total of " + total +" steps since I downloaded the app.")

                .build();
        fbShare.setShareContent(content);
    /*
        //Distance in Kilometers
        textView3 = (TextView) findViewById(R.id.textView3);
        float distance = (float) ((daily) * (settings.getInt("height", 0) * 0.414)) / (float) 100000; //0.414 cm = 0.0135 feet 6.2 (constant number to calulate stride length)
        textView3.setText("Daily Distance: " + distance);

        textView4 = (TextView) findViewById(R.id.textView4);

        if(settings.getBoolean("imperial", false)){
        double caloriesBurnedPerMile = (settings.getInt("weight", 0)) * 0.57;
        double conversionFactor = caloriesBurnedPerMile / 2200; //2200 is the amount of steps that has been assumed to take in a mile
        double caloriesBurned = daily * conversionFactor; // amount of calories burned with the number of steps has been taken in daily pedometer
        textView4.setText("Burned Calories: " + caloriesBurned);
    }else{
        double weightKg = (settings.getInt("weight", 0)) * .4536;
        double caloriesBurnedPerKm = weightKg * 0.57;
        double conversionFactorMet = caloriesBurnedPerKm / 1000; //2200 is the amount of steps that has been assumed to take in a mile
        double caloriesBurnedMet = daily * conversionFactorMet; // amount of calories burned with the number of steps has been taken in daily pedometer
            textView4.setText("Burned Calories: " + caloriesBurnedMet);
    }

*/

        textView2 = (TextView) findViewById(R.id.textView2);
        if(fbLogged)
        {
            textView2.setText("Welcome to Step On Your Friends, "+ profile.getName());
        }

        textView.setText("Total steps: " + total +" Daily steps: " + daily);

        final Handler mHandler = new Handler();
        Runnable continuousRunnable = new Runnable(){
            public void run() {

                SharedPreferences settings = getSharedPreferences("Pref_data", 0);
                int totalUpdate = settings.getInt("totalSteps", 0);
                int dailyUpdate = settings.getInt("dailySteps", 0);
                textView.setText("Total steps: " + totalUpdate +" Daily steps: " + dailyUpdate);

                //Distance in Kilometers
                textView3 = (TextView) findViewById(R.id.textView3);
                float distance = (float) ((dailyUpdate) * (settings.getInt("height", 0) * 0.414)) / (float) 100000; //0.414 cm = 0.0135 feet 6.2 (constant number to calulate stride length)
                textView3.setText("Daily Distance: " + distance);

                textView4 = (TextView) findViewById(R.id.textView4);

                if(settings.getBoolean("imperial", false)){
                    double caloriesBurnedPerMile = (settings.getInt("weight", 0)) * 0.57;
                    double conversionFactor = caloriesBurnedPerMile / 2200; //2200 is the amount of steps that has been assumed to take in a mile
                    double caloriesBurned = dailyUpdate * conversionFactor; // amount of calories burned with the number of steps has been taken in daily pedometer
                    textView4.setText("Burned Calories: " + caloriesBurned);
                }else{
                    double weightKg = (settings.getInt("weight", 0)) * .4536;
                    double caloriesBurnedPerKm = weightKg * 0.57;
                    double conversionFactorMet = caloriesBurnedPerKm / 1000; //2200 is the amount of steps that has been assumed to take in a mile
                    double caloriesBurnedMet = dailyUpdate * conversionFactorMet; // amount of calories burned with the number of steps has been taken in daily pedometer
                    textView4.setText("Burned Calories: " + caloriesBurnedMet);
                }

                mHandler.postDelayed(this, 10000);
            }
        };// Update text every second
        continuousRunnable.run();


        getSteps();

    }


    private void goToSettingsActivity(boolean fbLogged) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("fb", fbLogged);
        startActivity(intent);
    }

    private boolean getFbLogged()
    {
        return fbLogged;
    }

    private int getSteps()
    {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");


                    if (success) {
                        steperino = jsonResponse.getInt("steps");
                        textView2.setText("Total steps: " + steperino);

                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        GetStepRequest request = new GetStepRequest("flip", responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(request);
        return steperino;
    }




}

