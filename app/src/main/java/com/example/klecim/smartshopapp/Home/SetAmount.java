package com.example.klecim.smartshopapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.klecim.smartshopapp.R;


public class SetAmount extends AppCompatActivity {

    EditText edAmount;
    int customerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setamount);
        customerID =Integer.parseInt(getIntent().getExtras().getString("id"));
        edAmount = (EditText)findViewById(R.id.edamount);
        final Button btnAmount = (Button)findViewById(R.id.btnAmount);

        btnAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Amount = edAmount.getText().toString().trim();
                String customerIdToHome = String.valueOf(customerID);
                Bundle bundle = new Bundle();
                bundle.putString("amount",Amount);
                bundle.putString("id",customerIdToHome);
                Intent intentAmount = new Intent(getApplicationContext(),HomePage.class);
                intentAmount.putExtras(bundle);
                startActivity(intentAmount);


            }
        });

    }
}
