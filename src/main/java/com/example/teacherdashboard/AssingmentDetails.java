package com.example.teacherdashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class AssingmentDetails extends AppCompatActivity {
    int position;
    int id;
    JSONObject currentassing;
    String murl;
    ArrayList<JSONArray> submsns;
    TextView name,clas,subject,asngon,ldate,mmarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assingment_details);
        Intent intent=getIntent();
      position=  intent.getIntExtra("Assingment Position",0);
      name=findViewById(R.id.name);
      clas=findViewById(R.id.clas);
      subject=findViewById(R.id.subject);
      asngon=findViewById(R.id.Aon);
      ldate=findViewById(R.id.ldate);
      mmarks=findViewById(R.id.mmarks);
      submsns=new ArrayList<>();


        RequestQueue requestQueue= Volley.newRequestQueue(AssingmentDetails.this);
        final String url="https://api.litelesson.com/api/class/assignment/";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray details=response.getJSONArray("results");
                     currentassing=details.getJSONObject(position);
                    name.setText(currentassing.getString("questions"));
                    clas.setText("Class: "+currentassing.getString("class"));
                    subject.setText("Subject: "+currentassing.getString("subject"));
                    asngon.setText("Assinged On: "+currentassing.getString("assign_on").substring(0,10)+" "+"At: "+currentassing.getString("assign_on").substring(11,16));
                    ldate.setText("Last Date: "+currentassing.getString("last_submission").substring(0,10)+" "+"Till: "+currentassing.getString("last_submission").substring(11,16));
                    mmarks.setText("Maximum Marks: "+currentassing.getString("max_point"));
id=currentassing.getInt("id");

                } catch (JSONException e) {
                    Toast.makeText(AssingmentDetails.this,"!!"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AssingmentDetails.this,"!"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+MainActivity.token);
                return params;
            }
        };
        requestQueue.add(request);


    }


    public void Delete(View v){
        AlertDialog.Builder builder=new AlertDialog.Builder(AssingmentDetails.this);
        builder.setCancelable(false)
                .setTitle("Delete Assingment")
                .setMessage("Are you sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       deleteAssingment();
                    }
                })
                .setNegativeButton("No",null);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();



    }

    public void Edit(View v){
        Intent intent=new Intent(AssingmentDetails.this,EditAssingment.class);
        intent.putExtra("Current Item",id);
        startActivity(intent);

    }


    public void deleteAssingment(){
        try {
            int id=currentassing.getInt("id");
            RequestQueue queue=Volley.newRequestQueue(AssingmentDetails.this);
            String url="https://api.litelesson.com/api/class/assignment/?id="+id;
            JsonObjectRequest mrequest=new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(AssingmentDetails.this,"Assingment Deleted",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AssingmentDetails.this,MainActivity.class));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AssingmentDetails.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Token "+MainActivity.token);
                    return params;
                }
            };


            queue.add(mrequest);
        } catch (JSONException ex) {
            Toast.makeText(getApplicationContext(),"&&"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }



    public void show_pdf(View v)  {
        try {
            murl = currentassing.getJSONArray("attach_files").getString(0);



            Intent intent = new Intent();
            if (murl.contains("http://")) {
                murl = murl.replace("http", "https");
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(murl));
            }

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Unable to open pdf", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(), "No Files attached", Toast.LENGTH_SHORT).show();
        }
    }

    public void show_subm(View v) throws JSONException {
        Intent intent=new Intent(AssingmentDetails.this,Assingment_Submissions.class);
        intent.putExtra("Assingment Position",currentassing.getInt("id"));
        intent.putExtra("Assingment Name",currentassing.getString("questions"));
        startActivity(intent);

    }

}