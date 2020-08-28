package com.example.teacherdashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BaseNavigation extends AppCompatActivity {
Toolbar toolbar;
ActionBarDrawerToggle drawerToggle;
DrawerLayout drawerLayout;
NavigationView navigationView;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ntfy: {
                break;
            }
            case R.id.stngs: {
                startActivity(new Intent(getApplicationContext(), UserSettings.class));
                break;
            }

            case R.id.lgout: {
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, "https://api.litelesson.com/api/auth/logout/", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        startActivity(new Intent(getApplicationContext(),LoginActivy.class));
                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        startActivity(new Intent(getApplicationContext(),LoginActivy.class));
                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", "Token " + MainActivity.token);
                        return params;
                    }
                };
queue.add(request);
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_navigation);
        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.ldrawer);
        navigationView=findViewById(R.id.navigation);

        setSupportActionBar(toolbar);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.activity_dashboard:
                    {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        break;
                    }
                    case R.id.activity_Cassingment:{
                        startActivity(new Intent(getApplicationContext(),CreateAssingment.class));
                        break;
                    }
                    case R.id.activity_classSchedule:{
                        startActivity(new Intent(getApplicationContext(),ClassSchedule.class));

                        break;
                    }
                    case R.id.activity_startclass:{
                        startActivity(new Intent(getApplicationContext(),StartClass.class));
                        break;
                    }
                    case R.id.activty_Vassingment:{
                        startActivity(new Intent(getApplicationContext(),ViewAssingment.class));
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }


}