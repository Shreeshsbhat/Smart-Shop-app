package com.example.klecim.smartshopapp.Admin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.klecim.smartshopapp.API.SmartCard_API;
import com.example.klecim.smartshopapp.R;


import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    final String API_URL = SmartCard_API.REGISTER_URL;
    EditText edFirstName,edLastName,edMiddleName, edAddress,edCity,edEmail, edPassword, edPhoneNo,edPostpin;
    Button btnCustomerRegister;
    ToggleButton show;
    ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        edFirstName = (EditText) findViewById(R.id.firstname_input);
        edMiddleName = (EditText) findViewById(R.id.middlename_input);
        edLastName = (EditText) findViewById(R.id.lastname_input);
        edEmail = (EditText) findViewById(R.id.email_input);
        edCity = (EditText) findViewById(R.id.city_input);
        edPostpin = (EditText)findViewById(R.id.pincode_input);
        edPassword = (EditText) findViewById(R.id.password_input);
        edAddress = (EditText) findViewById(R.id.address_input);
        edPhoneNo = (EditText) findViewById(R.id.phone_no_input);
        show = (ToggleButton) findViewById(R.id.toggleButton2);

        btnCustomerRegister = (Button) findViewById(R.id.register_button);
        show.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(show.isChecked()){

                    edPassword.setInputType(InputType.TYPE_CLASS_TEXT  | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{

                    edPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        btnCustomerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adminRegister();
            }
        });


    }

    public void adminRegister(){


        final String firstName = edFirstName.getText().toString().trim();
        final String middleName = edMiddleName.getText().toString().trim();
        final String lastName = edLastName.getText().toString().trim();
        final String emailID = edEmail.getText().toString().trim();
        final String Address = edAddress.getText().toString().trim();
        final String cityName = edCity.getText().toString().trim();
        final String postPin = edPostpin.getText().toString().trim();
        final String Password= edPassword.getText().toString().trim();
        final String PhoneNumber = edPhoneNo.getText().toString().trim();

        if(firstName.toString().length()==0){
            Toast.makeText(getApplicationContext(), "Please Enter First Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(middleName.toString().length()==0){
            Toast.makeText(getApplicationContext(), "Please Enter Middle Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(lastName.toString().length()==0){
            Toast.makeText(getApplicationContext(), "Please Enter Last Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(emailID.toString().length()==0){
            Toast.makeText(getApplicationContext(), "Please Enter EmailID", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Address.toString().length()==0){

            Toast.makeText(getApplicationContext(),"Please Enter Address",Toast.LENGTH_SHORT).show();
            return;
        }
        if(cityName.toString().length()==0){
            Toast.makeText(getApplicationContext(), "Please Enter City", Toast.LENGTH_SHORT).show();
            return;
        }
        if(postPin.toString().length()==0 || (postPin.toString().length() < 6) || (postPin.toString().length() > 6)){
            Toast.makeText(getApplicationContext(), "Please Enter Pincode of length 6", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Password.toString().length()==0){

            Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(PhoneNumber.toString().length()==0 || PhoneNumber.toString().length()>10 || PhoneNumber.toString().length() < 10){

            Toast.makeText(getApplicationContext(),"Please Enter Phone Number of 10 digits only",Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("fname", firstName);
                params.put("mname", middleName);
                params.put("lname", lastName);
                params.put("email", emailID);
                params.put("address",Address);
                params.put("city", cityName);
                params.put("post_pin",postPin);
                params.put("password",Password);
                params.put("phone",PhoneNumber);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    /*@Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(Html.fromHtml("<font color='#FF7F27'>Exit!</font>"))
                .setMessage("Are You Sure Exit App ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Register.this.finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }*/

}

