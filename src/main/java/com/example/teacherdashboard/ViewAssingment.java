package com.example.teacherdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class ViewAssingment extends BaseNavigation {
RecyclerView rv;
    ArrayList<String> asngname;
    ArrayList<String> asngclas;
    ArrayList<String> asngldate;
    ArrayList<String> asngsubj;
    ArrayList<String> asngAdate;
    ArrayList<Integer> asngId;
    AssingmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_view_assingment);
        LayoutInflater inflater=LayoutInflater.from(this);
        View v=inflater.inflate(R.layout.activity_view_assingment,null,false);
        drawerLayout.addView(v,0);
        rv=findViewById(R.id.rv);
         asngname=new ArrayList<>();
        asngclas=new ArrayList<>();
        asngId=new ArrayList<>();
    asngldate=new ArrayList<>();
        asngsubj=new ArrayList<>();
        asngAdate =new ArrayList<>();



        retrieveAssing();



    }

    public void retrieveAssing(){
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://api.litelesson.com/api/class/assignment/";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray assingments=response.getJSONArray("results");

                    for(int i=0;i<assingments.length();i++){
                        JSONObject currentobject=assingments.getJSONObject(i);
                       asngsubj.add(currentobject.getString("subject"));
                        asngclas.add(currentobject.getString("class"));
                        asngldate.add(currentobject.getString("last_submission").substring(0,10));
                        asngAdate.add(currentobject.getString("assign_on").substring(0,10));
                        asngname.add(currentobject.getString("questions"));
                        asngId.add(currentobject.getInt("id"));

                    }
                  adapter =new AssingmentAdapter(getApplicationContext(),asngname,asngclas,asngldate,asngsubj,asngAdate);

                    adapter.OnitemClicked(new AssingmentAdapter.OnButtonClicked() {
                        @Override
                        public void OnViewClicked(int position) {
                            Intent intent=new Intent(ViewAssingment.this,AssingmentDetails.class);
                            intent.putExtra("Assingment Position",position);
                            startActivity(intent);
                        }

                        @Override
                        public void OnSubmissionClicked(int position) {
                            Intent intent=new Intent(ViewAssingment.this,Assingment_Submissions.class);
                            intent.putExtra("Assingment Position",asngId.get(position));
                            intent.putExtra("Assingment Name",asngname.get(position));
                            startActivity(intent);
                        }
                    });


                    rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(ViewAssingment.this));

                } catch (JSONException e) {
                    Toast.makeText(ViewAssingment.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+MainActivity.token);
                return params;
            }
        };
        queue.add(request);

    }


}