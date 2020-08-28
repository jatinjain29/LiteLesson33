package com.example.teacherdashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseNavigation {

String name,subject;
    public static String token;
    public static ArrayList<String> sclass;
    public static ArrayList<Integer> classid;
    TextView tname,tsubj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
        LayoutInflater inflater=LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_main, null, false);
        drawerLayout.addView(view, 0);
        tname=findViewById(R.id.tname);
        tsubj=findViewById(R.id.tsubj);
        Intent intent=getIntent();
        name=intent.getStringExtra("Name");
        subject=intent.getStringExtra("Subject");
        token=intent.getStringExtra("Token");

tname.setText("Name: "+name);
tsubj.setText("Subject: "+subject);
        sclass = new ArrayList<>();
        sclass.add(0, "Select Class...");
        classid = new ArrayList<>();



        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api.litelesson.com/api/auth/school/class/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray classes = response.getJSONArray("results");
                    for (int i = 0; i < classes.length(); i++) {
                        sclass.add(classes.getJSONObject(i).getString("name"));
                        classid.add(classes.getJSONObject(i).getInt("id"));

                    }

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + token);
                return params;
            }

        };
        queue.add(request);

    }


    public void Strtcls(View v) {
        startActivity(new Intent(MainActivity.this, StartClass.class));

    }


    public void ClsSchd(View v) {
        startActivity(new Intent(MainActivity.this, ClassSchedule.class));
    }

    public void CrtAsng(View v) {
        startActivity(new Intent(MainActivity.this, CreateAssingment.class));
    }

    public void VwAsng(View v) {
        startActivity(new Intent(MainActivity.this, ViewAssingment.class));
    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Toast.makeText(getApplicationContext(),"Press Logout icon to get logged out",Toast.LENGTH_SHORT).show();
    }
}


