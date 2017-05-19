package com.example.airuser.soyf10;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.content.Intent;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_lOAD_IMAGE = 1;
    ImageView imageToUpload;
    Button bUploadImage;
    EditText username;
    EditText firstname;
    EditText lastname;
    EditText age;
    EditText gender;
    EditText height;
    EditText weight;
    Switch privacy;
    Switch imperial;


    int heightInt;
    int weightInt;
    int ageInt;

    String savedAge;
    String savedHeight;
    String savedWeight;
    SharedPreferences settings;
    boolean checked;
    boolean impChecked;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checked = false;
        impChecked= false;
        setContentView(R.layout.profile_activity);
        settings = getSharedPreferences("Pref_data", 0);
        String bio = settings.getString("username","");
        String fname = settings.getString("firstname", "");
        String lname = settings.getString("lastname", "");
        ageInt = settings.getInt("age", 0);
        String savedGender = settings.getString("gender", "");
        heightInt = settings.getInt("height", 0);
        weightInt = settings.getInt("weight", 0);
        checked = settings.getBoolean("privacy", false);
        impChecked = settings.getBoolean("imperial", false);


        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        username = (EditText) findViewById(R.id.username);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        age = (EditText) findViewById(R.id.age);
        gender = (EditText) findViewById(R.id.gender);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        privacy = (Switch) findViewById(R.id.switch2);
        imperial = (Switch) findViewById(R.id.switch3);


        username.setText(bio);
        firstname.setText(fname);
        lastname.setText(lname);
        age.setText(Integer.toString(ageInt));
        gender.setText(savedGender);
        height.setText(Integer.toString(heightInt));
        weight.setText(Integer.toString(weightInt));
        privacy.setChecked(checked);
        imperial.setChecked(impChecked);

        imageToUpload.setOnClickListener(this);
        bUploadImage.setOnClickListener(this);
        privacy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton bw, boolean isChecked){
                checked = isChecked;
            }
        });

        imperial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton bw, boolean isChecked){
                impChecked = isChecked;
            }
        });

        }

    @Override
    public void onClick(View v) {
            switch(v.getId()){
                case R.id.imageToUpload:
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_lOAD_IMAGE);
                    break;
                case R.id.bUploadImage:
                    SharedPreferences.Editor editor = settings.edit();
                    savedHeight = height.getText().toString();
                    savedAge = age.getText().toString();
                    savedWeight = weight.getText().toString();
                    try {
                        heightInt = Integer.parseInt(savedHeight);
                        ageInt = Integer.parseInt(savedAge);
                        weightInt = Integer.parseInt(savedWeight);
                    }catch(Exception e){
                        Toast toast= Toast.makeText(getApplication(),"Invalid Input", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    editor.putString("username", username.getText().toString());
                    editor.putString("firstname", firstname.getText().toString());
                    editor.putString("lastname", lastname.getText().toString());
                    editor.putInt("age", ageInt);
                    editor.putString("gender", gender.getText().toString());
                    editor.putInt("height", heightInt);
                    editor.putInt("weight", weightInt);
                    editor.putBoolean("privacy", checked);
                    editor.putBoolean("imperial",impChecked);

                    editor.commit();
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_lOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);
            SharedPreferences.Editor editor = settings.edit();
            }
        }
    }





