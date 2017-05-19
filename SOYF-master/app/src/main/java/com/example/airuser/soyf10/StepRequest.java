package com.example.airuser.soyf10;

/**
 * Created by William on 4/25/2017.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StepRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://samplefish.000webhostapp.com/Step.php";
    private Map<String, String> params;
    Calendar calendar = Calendar.getInstance();




    public StepRequest(int step, String username, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        params = new HashMap<>();
        params.put("step", step+"");
        params.put("username", username);
        params.put("day", 4+"");
        params.put("month", 4+"");
        params.put("year",4+"");


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}