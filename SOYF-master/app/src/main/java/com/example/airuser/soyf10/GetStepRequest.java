package com.example.airuser.soyf10;

/**
 * Created by William on 4/25/2017.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;

import java.util.HashMap;
import java.util.Map;

public class GetStepRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://samplefish.000webhostapp.com/GetStep.php";
    private Map<String, String> params;

    public GetStepRequest(String username, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}