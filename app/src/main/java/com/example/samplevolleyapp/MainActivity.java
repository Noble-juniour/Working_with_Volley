package com.example.samplevolleyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView txtTitle,txtId,txtUserId,txtPostBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        Gson gson = new Gson();

        String url = "https://jsonplaceholder.typicode.com/posts";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Post posts = gson.fromJson(response,Post.class);
                txtId.setText(String.valueOf(posts.getId()));
                txtUserId.setText(String.valueOf(posts.getUserid()));
                txtTitle.setText(posts.getTitle());
                txtPostBody.setText(posts.getBody());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d(TAG, "onErrorResponse: error" + error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(1));
                map.put("userid",String.valueOf(2));
                map.put("title", "My first social media app");
                map.put("body", "Greetings World");
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.start();
    }
    //Responsible for initializing the views
    private void initViews()
    {
        Log.d(TAG, "initViews: started");
        txtTitle = (TextView) findViewById(R.id.title);
        txtId = (TextView) findViewById(R.id.idDetails);
        txtUserId = (TextView) findViewById(R.id.userId);
        txtPostBody = (TextView) findViewById(R.id.body);
    }
}
