package com.example.airuser.soyf10;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Profile;

import java.util.Calendar;

public class StepCounterService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor;
    private Calendar calendar;
    private SharedPreferences settings;
    private AccessToken accessToken;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        settings = getSharedPreferences("Pref_data", 0);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_YEAR);
        int dayOfYear = settings.getInt("dayOfYear",0);
        if(dayOfYear != today){
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("dailySteps", 0);
            editor.putInt("dayOfYear",today);
            editor.commit();
        }
        Sensor sensor = event.sensor;
        if(accessToken != null)
        {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            Profile profile = Profile.getCurrentProfile();
            String facebookID = accessToken.getUserId();
        }



        if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

            int total = settings.getInt("totalSteps", 0);
            int daily = settings.getInt("dailySteps", 0);



            total++;
            daily++;
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                public void onResponse(String response) {


                }

            };
            StepRequest stepRequest = new StepRequest(total, "flip", responseListener);
            RequestQueue queue = Volley.newRequestQueue(StepCounterService.this);
            queue.add(stepRequest);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("totalSteps", total);
            editor.putInt("dailySteps", daily);
            editor.commit();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
