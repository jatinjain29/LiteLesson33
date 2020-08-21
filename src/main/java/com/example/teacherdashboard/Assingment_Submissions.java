package com.example.teacherdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Assingment_Submissions extends AppCompatActivity {
int assng_id;
TextView tvdisplaay;
ArrayList<Submission> allSubmission;
RecyclerView rv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assingment__submissions);
        Intent intent=getIntent();
       assng_id= intent.getIntExtra("Assingment Position",0);
       allSubmission=new ArrayList<>();
       rv3=findViewById(R.id.rv3);
       tvdisplaay=findViewById(R.id.tvdisplay);
       tvdisplaay.setText(intent.getStringExtra("Assingment Name"));
       fetchSubmissions();

    }



    public void fetchSubmissions(){
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url="https://api.litelesson.com/api/class/submitassign/?qid="+String.valueOf(assng_id);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results=response.getJSONArray("results");
                    for(int i=0;i<results.length();i++) {
                        JSONObject currentObj = (JSONObject) results.getJSONObject(i);
                        Submission crnt_subm = new Submission(currentObj.getString("optional_message"),
                                currentObj.getString("student"),
                                currentObj.getString("subject"),
                                currentObj.getString("submitted_on").substring(0,10),
                                currentObj.getJSONArray("attach_files")

                        );

                   //    Toast.makeText(getApplicationContext(),currentObj.get("attach_files").toString(),Toast.LENGTH_LONG).show();
                        allSubmission.add(crnt_subm);
                    }
if(allSubmission.size()==0){

    Toast.makeText(getApplicationContext(),"No Submissions",Toast.LENGTH_SHORT).show();

}
                   else {
    Submission_Adapter adapter = new Submission_Adapter(getApplicationContext(), allSubmission);
    adapter.show_fileClickListener(new Submission_Adapter.Onsubmissionclicked() {
        @Override
        public void btnshowfile(int position) {
            String furi=" ";
            try {
                furi = allSubmission.get(position).getAttach_files().get(0).toString();
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "No files attached by student", Toast.LENGTH_SHORT).show();
            }

            if (furi.contains("http://")) {
                furi = furi.replace("http", "https");
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(furi));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }else{
                //Page not found
                Toast.makeText(getApplicationContext(),"Unable to open pdf",Toast.LENGTH_SHORT).show();
            }
        }
    });
    rv3.setLayoutManager(new LinearLayoutManager(Assingment_Submissions.this));
    rv3.setAdapter(adapter);
}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+MainActivity.token);
                return params;
            }

        };
        requestQueue.add(request);

    }


}