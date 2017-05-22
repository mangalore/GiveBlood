package com.example.ananya.giveblood.service.impl;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.ananya.giveblood.util.Utility;

import java.util.HashMap;
import java.util.Map;

public class VolleyStringRequest extends StringRequest {

    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private String mBody;

    public VolleyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        mHeaders = new HashMap<>();
        mParams = new HashMap<>();
        addCommonHeaders();
    }

    private void addCommonHeaders() {
        addHeaders(Utility.AUTHORIZATION, getAuthString());
    }

    private String getAuthString() {
        String authStr = String.format("%s:%s", Utility.USERNAME_VALUE, Utility.PASSWORD_VALUE);
        byte[] authData = Base64.encode(authStr.getBytes(), 1);
        String authString = new String(authData);
        return String.format("Basic %s", authString);
    }

    public void addHeaders(String key, String value) {
        mHeaders.put(key, value);
    }

    public void addParams(String key, String value) {
        mParams.put(key, value);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mBody.getBytes();
    }

    public void setBody(String body) {
        this.mBody = body;
    }
}
