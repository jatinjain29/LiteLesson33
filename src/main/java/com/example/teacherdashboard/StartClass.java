package com.example.teacherdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StartClass extends BaseNavigation {
Spinner spinner;
 ArrayList<String> studclass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      // setContentView(R.layout.activity_start_class);

        LayoutInflater inflater=LayoutInflater.from(this);
      View vw=inflater.inflate(R.layout.activity_start_class,null,false);
        drawerLayout.addView(vw,0);
        spinner=findViewById(R.id.spinner);

        studclass=MainActivity.sclass;
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(StartClass.this,R.layout.custom_spinner,studclass);
        spinner.setAdapter(arrayAdapter);
    }


}