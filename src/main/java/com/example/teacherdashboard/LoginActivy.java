package com.example.teacherdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivy extends AppCompatActivity {
EditText usrname,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activy);
        usrname=findViewById(R.id.usrname);
        password=findViewById(R.id.password);}


    public void login(View v)  {

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://api.litelesson.com/api/auth/sadmin/login/";
        if(TextUtils.isEmpty(usrname.getText().toString())){
            usrname.setError("Username Required");
            return;

        }

        if(TextUtils.isEmpty(password.getText().toString())){
           password.setError("Password Required");
            return;

        }
        JSONObject details=new JSONObject();
        try {
            details.put("username",usrname.getText().toString());
            details.put("password",password.getText().toString());
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject data = response.getJSONObject("data");
                    String name=data.getString("name");
                   String subject= data.getString("subject");
                    String  token=response.getString("token");
                    Intent intent=new Intent(LoginActivy.this,MainActivity.class);
                    intent.putExtra("Name",name);
                    intent.putExtra("Subject",subject);
                    intent.putExtra("Token",token);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    queue.add(request);
    }
}