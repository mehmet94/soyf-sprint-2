package com.example.airuser.soyf10;

import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;


import com.facebook.*;
import com.facebook.Profile;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import org.json.JSONObject;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    private TextView textView3;//Distance
    private TextView textView4;//Calorie
    private ShareButton fbShare;
    private Profile profile;
    public boolean fbLogged;

    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent fromLogin = getIntent();

        fbLogged = false;
        fbLogged = getIntent().getExtras().getBoolean("fb");
        if(fbLogged)
        {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            profile = Profile.getCurrentProfile();
        }


        Bundle parameters = new Bundle();

        Intent intent = new Intent(this, StepCounterService.class);
        startService(intent);
        SharedPreferences settings = getSharedPreferences("Pref_data", 0);
        editor = settings.edit();

        Calendar calendar = Calendar.getInstance();
        int day = settings.getInt("dayOfYear", 0);
        int year = settings.getInt("year", 0);
        if(calendar.get(Calendar.DAY_OF_YEAR) != day || calendar.get(Calendar.YEAR) != year){
            editor.putInt("dailyStep", 0);
        }

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("dayOfYear", calendar.get(Calendar.DAY_OF_YEAR));
        editor.putInt("year", calendar.get(Calendar.YEAR));
        editor.commit();

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
        int daily = settings.getInt("totalSteps", 0);
        textView = (TextView) findViewById(R.id.textView);

        fbShare = (ShareButton) findViewById(R.id.fbShare);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://cecs492.weebly.com/"))
                .setContentDescription("Step On Your Friends")
                .setContentTitle("I've take a total of " + total +" steps since i downloaded the app.")
                .build();
        fbShare.setShareContent(content);

        //Distance in Kilometers
        textView3 = (TextView) findViewById(R.id.textView3);
        float distance = (float)((daily)*(settings.getInt("height", 0)*0.414))/(float)100000; //0.414 cm = 0.0135 feet 6.2 (constant number to calulate stride length)
        textView3.setText("Daily Distance: " + distance);

        textView4 = (TextView) findViewById(R.id.textView4);
        double caloriesBurnedPerMile = (settings.getInt("weight", 0)) * 0.57;
        double conversionFactor = caloriesBurnedPerMile / 2200; //2200 is the amount of steps that has been assumed to take in a mile
        double caloriesBurned = daily * conversionFactor; // amount of calories burned with the number of steps has been taken in daily pedometer
        textView4.setText("Burned Calories: " + caloriesBurned);

        textView2 = (TextView) findViewById(R.id.textView2);
        if(fbLogged)
        {
            textView2.setText("Welcome to Step On Your Friends, "+ profile.getName());
        }

        textView.setText("Total steps: " + total +" Daily steps: " + daily);


    }





    protected void onStop() {
        super.onStop();


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





}

