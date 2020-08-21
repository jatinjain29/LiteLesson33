package com.example.teacherdashboard;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.DataTruncation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAssingment extends BaseNavigation {
EditText Ddate,AssngName,Dtime,MaxMarks;
Spinner spinclass;
ArrayList<String> classes;
ArrayList<Integer> classid;
Button attachfile;
 int cyear,cmonth,cday,chour,cminute,csecond;
DatePickerDialog.OnDateSetListener datecallback;
TimePickerDialog.OnTimeSetListener timecallback;
 Calendar c=Calendar.getInstance();
    JSONArray myarry;

 String encodedPDf1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater=LayoutInflater.from(this);
        View v=inflater.inflate(R.layout.activity_create_assingment,null,false);
        drawerLayout.addView(v,0);
        Ddate = findViewById(R.id.etDueDate);
        attachfile=findViewById(R.id.btn_attach);
        Dtime = findViewById(R.id.etDueTime);
        AssngName = findViewById(R.id.etAsngNam);
        spinclass = findViewById(R.id.spinClass);
        MaxMarks = findViewById(R.id.etMaxMarks);
        classes=MainActivity.sclass;
        classid=MainActivity.classid;
        myarry=new JSONArray();


        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(CreateAssingment.this,R.layout.custom_spinner,classes);
        spinclass.setAdapter(arrayAdapter);


        datecallback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int fmonth=month+1;
                Ddate.setText(year + "-" + fmonth + "-" + dayOfMonth);
            }
        };

                timecallback = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Dtime.setText(hourOfDay + ":" + minute + ":" + "00");
                    }
                };

            }


            public  void PickDate(View v) {
                cyear = c.get(Calendar.YEAR);
                cmonth = c.get(Calendar.MONTH);
                cday = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateAssingment.this, datecallback, cyear, cmonth, cday);
                datePickerDialog.show();

            }


            public  void PickTime(View v) {
                chour = c.get(Calendar.HOUR_OF_DAY);
                cminute = c.get(Calendar.MINUTE);
                csecond = c.get(Calendar.SECOND);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateAssingment.this, timecallback, chour, cminute, true);
                timePickerDialog.show();

            }


            public void CreatAssingment() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="https://api.litelesson.com/api/class/assignment/";
                JSONObject MyData = new JSONObject();





              try {
                  if(spinclass.getSelectedItem().toString()=="Select Class..." || spinclass==null){
                      Toast.makeText(getApplicationContext(),"Please Select Class",Toast.LENGTH_SHORT).show();
                      return;
                  }
                  else {
                      MyData.put("class_id", classid.get(spinclass.getSelectedItemPosition() - 1));
                  }
                 if(TextUtils.isEmpty(AssngName.getText().toString())){
                     AssngName.setError("Assingment Name required");
                     return;
                 }
                 else {
                     MyData.put("questions", AssngName.getText().toString());
                 }
                 if(TextUtils.isEmpty(MaxMarks.getText().toString())){
                     MaxMarks.setError("Enter Maximum Marks");
                     return;
                 }
                 else {
                     MyData.put("max_point", MaxMarks.getText().toString());
                 }

                 if(TextUtils.isEmpty(Ddate.getText().toString()) || TextUtils.isEmpty(Dtime.getText().toString())){
                     Toast.makeText(getApplicationContext(),"Due date and time required",Toast.LENGTH_SHORT).show();
                     return;
                 }
                   else{
                       MyData.put("last_submission", Ddate.getText().toString() + "T" + Dtime.getText().toString());
                 }
                    if(encodedPDf1!=null){
                        String tosend="data:application/pdf;base64,"+encodedPDf1;
                        myarry.put(tosend);
                        MyData.put("attach_files",myarry);
                    }


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, MyData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateAssingment.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "error:"+error.toString());
                        error.printStackTrace();
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


            public void SaveAsng(View v) {

                CreatAssingment();

            }


            public void attach(View v){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
       intent.setType("application/pdf");
        startActivityForResult(intent,2);

            }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && data!=null ) {
           Uri uri =data.getData();

           try {
               InputStream inputStream = CreateAssingment.this.getContentResolver().openInputStream(uri);
               byte[] pdfInbytes =new byte[inputStream.available()];
               inputStream.read(pdfInbytes);

               encodedPDf1=Base64.encodeToString(pdfInbytes,Base64.NO_WRAP);
               inputStream.close();
               if(encodedPDf1!=null){

                   Toast.makeText(getApplicationContext(),"Document Selected ",Toast.LENGTH_SHORT).show();
                   attachfile.setText("Change File");
               }


           }
           catch (FileNotFoundException e) {
               Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
           } catch (IOException e) {
               Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
               e.printStackTrace();
           }
       }
       else {
           Toast.makeText(getApplicationContext(),"Error in result method",Toast.LENGTH_SHORT).show();
       }

    }
}
