package com.example.airuser.soyf10;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.facebook.AccessToken;

import java.util.HashMap;
import java.util.Map;
import com.facebook.*;
import com.facebook.Profile;

/**
 * Created by William on 4/25/2017.
 */

public class FacebookRegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://samplefish.000webhostapp.com/FacebookRegister.php";
    private Map<String, String> params;

    public FacebookRegisterRequest(String username, String password, String facebookID, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();
        params = new HashMap<>();
        params.put("name", profile.getName());
        params.put("username", username);
        params.put("password",password);

        params.put("facebookID", facebookID);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
