package com.example.airuser.soyf10;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {
    LoginButton login;
    CallbackManager cbManager;
    AccessToken accessToken;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button button = (Button) findViewById(R.id.buttonTutorial);
        Button profiles = (Button) findViewById(R.id.profilesButton);
        Button unlink = (Button) findViewById(R.id.unlink);
        cbManager = CallbackManager.Factory.create();

       // LoginButton faceb = (LoginButton) findViewById(R.id.FACEBOOKButton);
        //AccessToken accessToken = AccessToken.getCurrentAccessToken();

       // LoginManager.getInstance().logOut();

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsActivity.this, Login.class);
                LoginManager.getInstance().logOut();
                startActivity(intent);
            }
        });

        profiles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToProfilesActivity();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTutorialActivity();
            }
        });

        unlink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                                builder.setMessage("Unlinking succeedeed.")
                                        .setNegativeButton("Done", null)
                                        .create()
                                        .show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                                builder.setMessage("Unlinking failed.")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                };

                boolean fbLogged = getIntent().getExtras().getBoolean("fb");
                if(fbLogged)
                {
                    AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    com.facebook.Profile profile = com.facebook.Profile.getCurrentProfile();
                    id = accessToken.getUserId();
                }


                UnlinkRequest request = new UnlinkRequest(id ,responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
                queue.add(request);
            }

        });



    }

    private void goToProfilesActivity() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    private void goToTutorialActivity() {
        Intent intent = new Intent(this, Tutorial.class);
        startActivity(intent);
    }


}
