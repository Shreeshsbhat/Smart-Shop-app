package com.example.klecim.smartshopapp.Home;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.klecim.smartshopapp.R;
import com.example.klecim.smartshopapp.SmartShopObjects.Objects;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.klecim.smartshopapp.API.SmartCard_API.FINALIZE_BILL_URL;

/**
 * Created by Arunkumar on 11/5/2016.
 */
public class FinalBillAndItems extends AppCompatActivity {

    ListView allBooks;
    String purchaseId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_bill);
        Bundle extras = getIntent().getExtras();
        purchaseId = extras.getString("purchaseId");
// Add the buttons



// Create the AlertDialog
        ArrayList<Objects> arraylist  = extras.getParcelableArrayList("arrayList");
        //ArrayList<Objects> list = new ArrayList<>(arraylist);
        int amount = Integer.parseInt(getIntent().getExtras().getString("amount"));
        TextView tvfinalBill = (TextView)findViewById(R.id.tvBill);

        for(int i = 0 ;i<arraylist.size();i++) {
            System.out.print("-------------------" + arraylist);
            Objects obj =  arraylist.get(i);
            String name = obj.getItemName();
            String price = String.valueOf(obj.getItemPrice());

            final ArrayAdapter<Objects> mArrayAdapter = new ArrayAdapter<Objects>(getApplicationContext(),
                    R.layout.mylist,R.id.driver ,arraylist);
//            allBooks.setAdapter(mArrayAdapter);

        }
        tvfinalBill.setText("\n Amount : " + amount);
    }

    private void showForgotDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Pin")
                .setMessage("Enter your pin number?")
                .setView(taskEditText)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataToServer();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void buttonClicked(View v){
        showForgotDialog(FinalBillAndItems.this);
    }

    public  void dataToServer(){
//        Toast.makeText(this, v.getText(),Toast.LENGTH_LONG).show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, FINALIZE_BILL_URL+"?purchase_id="+purchaseId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), purchaseId+"", Toast.LENGTH_SHORT).show();

                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();

                        Log.i("INFO", response);
                        finish();
                        //finalBill();

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

                params.put("purchase_id",purchaseId);

                //params.put("balance",String.valueOf(bugetAmount));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

}
