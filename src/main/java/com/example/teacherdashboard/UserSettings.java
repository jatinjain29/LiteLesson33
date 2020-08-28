package com.example.teacherdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserSettings extends AppCompatActivity {
EditText ename,esubj,eopassw,enpassw,ecnpassw,ecpassw;
String url="https://api.litelesson.com/api/auth/teacher/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        ename=findViewById(R.id.ename);
        esubj=findViewById(R.id.esubject);
        eopassw=findViewById(R.id.opassw);
        ecnpassw=findViewById(R.id.cnpassw);
        enpassw=findViewById(R.id.npassw);
        ecpassw=findViewById(R.id.cpassw);





    }

    public void SaveDetails(View view) {
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JSONObject details=new JSONObject();
        try {
            if (!TextUtils.isEmpty(ename.getText())) {
                details.put("name", ename.getText().toString());
            }

            if (!TextUtils.isEmpty(esubj.getText())) {
                details.put("subject", esubj.getText().toString());
            }
            if (!TextUtils.isEmpty(eopassw.getText())) {
                details.put("opassword", eopassw.getText().toString());
            } else {
                eopassw.setError("Current Password required");
                return;
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }


        JsonObjectRequest request=new JsonObjectRequest(Request.Method.PUT, url, details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }


        })
        {
            @Override
            public Map<String, String> getHeaders () throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + MainActivity.token);
                return params;
            }

        };
        queue.add(request);

    }












    public void ChangePassword(View view) {
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JSONObject details=new JSONObject();
        try {
            if (!TextUtils.isEmpty(ecpassw.getText().toString())) {
                details.put("opassword", ecpassw.getText().toString());
            }
            else{
                ecpassw.setError("Please enter current Password");
                return;
            }

            if (!TextUtils.isEmpty(enpassw.getText())) {
                details.put("password", enpassw.getText().toString());
            }
            else{
                enpassw.setError("Please enter new Password");
                return;
            }
            if (!TextUtils.isEmpty(ecnpassw.getText())) {
                if(!ecnpassw.getText().toString().equals(enpassw.getText().toString()))
                {
                    ecnpassw.setError("Password does not match");
                    return;
                }
               // details.put("subject", enpassw.getText().toString());
            }
            else{
                ecnpassw.setError("Please enter Password to confirm");
                return;
            }




        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }


        JsonObjectRequest request=new JsonObjectRequest(Request.Method.PUT, url, details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }


        })
        {
            @Override
            public Map<String, String> getHeaders () throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + MainActivity.token);
                return params;
            }
        };
queue.add(request);
    }





}