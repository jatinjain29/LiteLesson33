package com.example.teacherdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassSchedule extends BaseNavigation {
RecyclerView rv;
String token=MainActivity.token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_class_schedule);
        LayoutInflater inflater=LayoutInflater.from(this);
        View v=inflater.inflate(R.layout.activity_class_schedule,null,false);
        drawerLayout.addView(v,0);
        rv=findViewById(R.id.rv2);



        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api.litelesson.com/api/class/timetable/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject classes = response.getJSONObject("results");
                    Map<String,Object> daywise=new HashMap<>();
                    ArrayList<ArrayList<ScheduleClass>> complete=new ArrayList<>();
                    for (int i = 0; i < classes.length(); i++) {
                        JSONArray currentday = classes.getJSONArray(rtnDay(i));
                        ArrayList<ScheduleClass> day = new ArrayList<>();

                        for (int j = 0; j < currentday.length(); j++) {
                            JSONObject dayobject=currentday.getJSONObject(j);
                            ScheduleClass dayclass = new ScheduleClass(dayobject.getString("day"),
                                    dayobject.getString("start_time")
                                    , dayobject.getString("end_time")
                                    , dayobject.getString("class")
                            );
                            day.add(dayclass);
                        }

                        complete.add(day);
                    }
                        OClass_Adapter adapter=new OClass_Adapter(getApplicationContext(),complete);
                        rv.setLayoutManager(new LinearLayoutManager(ClassSchedule.this));
                        rv.setAdapter(adapter);



                } catch (JSONException e) {
                    Toast.makeText(ClassSchedule.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public String rtnDay(int i){
        if(i==0){
            return "monday";
        }
        else if(i==1){
            return "tuesday";
        }
        else  if(i==2){
            return "wednesday";
        }
        else  if(i==3){
            return "thursday";
        }
        else if(i==4){
            return "friday";
        }
        else if(i==5){
            return "saturday";
        }
        else if(i==6){
            return "sunday";
        }
        return "monday";
    }

    }
