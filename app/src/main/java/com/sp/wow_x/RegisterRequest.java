package com.sp.wow_x;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;
import java.util.HashMap;
/**
 * Created by Law Wen Xin on 15/1/2018.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://172.22.14.107/wowx/process_data/register-acc.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, String password, Response.Listener<String> listener ){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
