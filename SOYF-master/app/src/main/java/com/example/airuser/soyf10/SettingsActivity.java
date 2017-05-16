package com.example.airuser.soyf10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class SettingsActivity extends AppCompatActivity {
    LoginButton login;
    CallbackManager cbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button button = (Button) findViewById(R.id.buttonTutorial);
        Button profiles = (Button) findViewById(R.id.profilesButton);

        cbManager = CallbackManager.Factory.create();

       // LoginButton faceb = (LoginButton) findViewById(R.id.FACEBOOKButton);
        //AccessToken accessToken = AccessToken.getCurrentAccessToken();

       // LoginManager.getInstance().logOut();

        login = (LoginButton) findViewById(R.id.fbLogin);

        login.registerCallback(cbManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(SettingsActivity.this, Login.class);
                SettingsActivity.this.startActivity(intent);

            }
                    public void onCancel() {

                    }


                    @Override
                    public void onError(FacebookException error) {

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
