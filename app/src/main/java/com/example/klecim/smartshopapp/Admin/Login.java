package com.example.klecim.smartshopapp.Admin;

/**
 * Created by Arunkumar on 10/7/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.klecim.smartshopapp.Home.HomePage;
import com.example.klecim.smartshopapp.Home.SetAmount;
import com.example.klecim.smartshopapp.R;
import com.example.klecim.smartshopapp.API.SmartCard_API;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {


    EditText edPhone,edPassword;
    Button btnLogin;
    CheckBox passTog;
    TextView notYetRegister;
    final String API_URL = SmartCard_API.LOGIN_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edPhone = (EditText)findViewById(R.id.phone_no_input);
        edPassword = (EditText)findViewById(R.id.password_input);
        btnLogin = (Button)findViewById(R.id.register_button);
        notYetRegister = (TextView)findViewById(R.id.tvNotYetRegister);
        passTog = (CheckBox)findViewById(R.id.checkBox2);

        passTog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        notYetRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getApplicationContext(),Register.class);
                startActivity(register);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdminLogin();
            }
        });

    }

    public void AdminLogin(){

        final String phoneNo = edPhone.getText().toString().trim();
        //final String Name = edName.getText().toString().trim();
        final String password= edPassword.getText().toString().trim();
        //final String PhoneNumber = edMobile.getText().toString().trim();
        if(phoneNo.toString().length()==0){

            Toast.makeText(getApplicationContext(),"Please Enter Phone Number",Toast.LENGTH_SHORT).show();
            return;
        }


        if(password.toString().length()==0){
            Toast.makeText(getApplicationContext(), "Please Enter Phone No", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.toString().length()==0){

            Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }



        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            //String status=jsonObject.get("status").toString();
                            String status = jsonObject.getString("status");
                            String customer_id = jsonObject.get("customer_id").toString();


                            if(status.equalsIgnoreCase("success"))
                            {
                                Intent adminLogin = new Intent(getApplicationContext(), HomePage.class);
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
                                Bundle bundle = new Bundle();
                                bundle.putString("id",customer_id);
                                adminLogin.putExtras(bundle);
                                startActivity(adminLogin);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Login is Failed",Toast.LENGTH_SHORT).show();

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
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
                params.put("phone", phoneNo);
                params.put("password",password);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



    /*@Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(Html.fromHtml("<font color='#FF7F27'>Exit!</font>"))
                .setMessage(Html.fromHtml("<b>Are You Sure Exit App ?</b>"))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Login.this.finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }*/




}

