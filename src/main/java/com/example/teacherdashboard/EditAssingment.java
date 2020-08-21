package com.example.teacherdashboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditAssingment extends AppCompatActivity {
    EditText asngName, asngMm, asngDdate, asngDtime;
    Spinner asngClas;
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener Duedatecallback;
    TimePickerDialog.OnTimeSetListener Duetimecallback;
    String encodedPDf2;
    JSONArray myarry;
    int myear, mmonth, mday, mhour, mminute;
    int id;
    ArrayList<String> classes;
    ArrayList<Integer> classid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assingment);
        asngName = findViewById(R.id.etname);
        asngClas = findViewById(R.id.etclass);
        asngMm = findViewById(R.id.etmax);
        asngDdate = findViewById(R.id.eDueDate);
        asngDtime = findViewById(R.id.eDueTime);
        myarry = new JSONArray();

        Duedatecallback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int fmonth = month + 1;

                asngDdate.setText(year + "-" + fmonth + "-" + dayOfMonth);
            }
        };

        Duetimecallback = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                asngDtime.setText(hourOfDay + ":" + minute + ":" + "00");
            }
        };


        Intent intent = getIntent();
        id = intent.getIntExtra("Current Item", 0);
        classes = MainActivity.sclass;
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_spinner, classes);
        asngClas.setAdapter(spinneradapter);
        classid = MainActivity.classid;


    }

    public void Update(View v) {
        String url = "https://api.litelesson.com/api/class/assignment/";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject updatedata = new JSONObject();
        try {
            try {
                updatedata.put("id", id);
                if (!asngName.getText().toString().isEmpty()) {
                    updatedata.put("questions", asngName.getText().toString());
                }
                if (asngClas.getSelectedItem() != null) {
                    updatedata.put("class_id", classid.get(asngClas.getSelectedItemPosition() - 1));
                }
                if (!asngMm.getText().toString().isEmpty()) {
                    updatedata.put("max_point", asngMm.getText().toString());
                }
                if (!asngDdate.getText().toString().isEmpty()) {
                    updatedata.put("last_submission", asngDdate.getText().toString() + "T" + asngDtime.getText().toString());
                }
                if (encodedPDf2 != null) {
                    String tosend = "data:application/pdf;base64," + encodedPDf2;
                    myarry.put(tosend);
                    updatedata.put("attach_files", myarry);
                }


            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, updatedata, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(EditAssingment.this, response.toString(), Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EditAssingment.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Token " + MainActivity.token);
                    return params;
                }


            };
            requestQueue.add(request);
        }
        catch (Exception e) {

            Toast.makeText(getApplicationContext(), "No Changes made", Toast.LENGTH_SHORT).show();
        }


    }

    public void Discard(View v) {
        asngName.setText(null);
        asngDdate.setText(null);
        asngMm.setText(null);
        asngClas.setSelection(0);
    }

    public void PickDate1(View v) {
        myear = calendar.get(Calendar.YEAR);
        mmonth = calendar.get(Calendar.MONTH);
        mday = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditAssingment.this, Duedatecallback, myear, mmonth, mday);
        datePickerDialog.show();

    }

    public void PickTime1(View v) {
        mhour = calendar.get(Calendar.HOUR_OF_DAY);
        mminute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(EditAssingment.this, Duetimecallback, mhour, mminute, true);
        timePickerDialog.show();

    }


    public void Choose(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = EditAssingment.this.getContentResolver().openInputStream(uri);
                byte[] pdfInbytes = new byte[inputStream.available()];
                inputStream.read(pdfInbytes);

                encodedPDf2 = Base64.encodeToString(pdfInbytes, Base64.NO_WRAP);
                inputStream.close();
                if (encodedPDf2 != null) {
                    Toast.makeText(getApplicationContext(), "Document Selected ", Toast.LENGTH_SHORT).show();
                }


            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Error in result method", Toast.LENGTH_SHORT).show();
        }
    }
}