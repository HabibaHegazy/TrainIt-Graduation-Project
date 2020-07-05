package com.example.ipingpong.shared.datasource.remote;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ipingpong.shared.datasource.Constants;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestApi {

    private Context context;

    public RequestApi(Context context) {
        this.context = context;
    }

    public void selectApi(final VolleyCallBack callback, final String tableName, Map<String, String> conditions) {

        Gson gson = new Gson();
        final String con = gson.toJson(conditions);

        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_SELECT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                System.out.println(response);

                try {
                    callback.onSuccess(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.onFailure(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("table", tableName);
                param.put("conditions", con);

                return param;
            }
        };

        System.out.println(request);

        VolleySingleton.getnInstance(context).addRequestQue(request);
    }

    public void reportApi(final VolleyCallBack callback, final String tableName, Map<String, String> conditions) {

        Gson gson = new Gson();
        final String con = gson.toJson(conditions);

        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_REPORT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                System.out.println(response);

                try {
                    callback.onSuccess(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.onFailure(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("table", tableName);
                param.put("conditions", con);

                return param;
            }
        };

        VolleySingleton.getnInstance(context).addRequestQue(request);
    }

    public void insertApi(final VolleyCallBack callback, final String tableName, Map<String, String> data) {

        Gson gson = new Gson();
        final String insertData = gson.toJson(data);


        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_INSERT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                System.out.println(response);

                try {
                    callback.onSuccess(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.onFailure(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("table", tableName);
                param.put("data", insertData);

                return param;
            }
        };

        VolleySingleton.getnInstance(context).addRequestQue(request);
    }

    public void updateApi(final VolleyCallBack callback, final String tableName, Map<String, String> data, Map<String, String> conditions) {

        Gson gson = new Gson();
        final String update_data = gson.toJson(data);
        final String update_con = gson.toJson(conditions);


        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_UPDATE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println(response);

                try {
                    callback.onSuccess(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.onFailure(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("table", tableName);
                param.put("data", update_data);
                param.put("conditions", update_con);

                return param;
            }
        };

        VolleySingleton.getnInstance(context).addRequestQue(request);

    }

    public void deleteApi(final VolleyCallBack callback, final String tableName, Map<String, String> conditions) {

        Gson gson = new Gson();
        final String insert_data = gson.toJson(conditions);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.URL_DELETE, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.onFailure(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("table", tableName);
                param.put("data", insert_data);

                return param;
            }
        };

        VolleySingleton.getnInstance(context).addRequestQue(request);

    }

}
