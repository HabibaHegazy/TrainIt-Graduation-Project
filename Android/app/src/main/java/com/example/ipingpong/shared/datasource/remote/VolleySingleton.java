package com.example.ipingpong.shared.datasource.remote;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleySingleton {
    private static VolleySingleton nInstance;
    private RequestQueue RQ;
    private Context CTX;

    private VolleySingleton(Context context)
    {
        CTX=context;
        RQ=getRequestQue();
    }

    public RequestQueue getRequestQue()
    {
        if(RQ==null)
        {
            RQ= Volley.newRequestQueue(CTX.getApplicationContext());
        }
        return RQ;
    }

    public static synchronized VolleySingleton getnInstance(Context context)
    {
        if(nInstance==null)
        {
            nInstance=new VolleySingleton(context);
        }
        return nInstance;
    }

    public <T> void addRequestQue(Request<T> request)
    {
        RQ.add(request);
    }



}
