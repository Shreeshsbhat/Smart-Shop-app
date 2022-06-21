package com.example.klecim.smartshopapp.Home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.klecim.smartshopapp.API.SmartCard_API;
import com.example.klecim.smartshopapp.R;
import com.example.klecim.smartshopapp.SmartShopObjects.Item;
import com.example.klecim.smartshopapp.SmartShopObjects.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.klecim.smartshopapp.API.SmartCard_API.BILL_URL;


public class HomePage extends AppCompatActivity {

    //String Id = null;


    ListView view;
    ArrayList<Objects> arrayList = new ArrayList<Objects>();
    ArrayList<Objects> newArray = new ArrayList<Objects>();
    ArrayList<Item> items = new ArrayList<>();
    String barCode=null,itemName;
    TextView tvStatus,tvResult,tvbarCode;
    String cancateBarcode="";
    int itemPrice;
    int  bugetAmount,customerId,totalAmount;
    int i =0;
    final String API_URL = SmartCard_API.ITEM_URL;
    ArrayAdapter<Item> mArrayAdapter1;
    final String FINAL_API_URL = BILL_URL;
    AlertDialog.Builder builder ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        bugetAmount =10000;
        customerId = Integer.parseInt(getIntent().getExtras().getString("id"));
        tvStatus=(TextView)findViewById(R.id.tvStatus);
         tvResult=(TextView)findViewById(R.id.tvResult);
        tvbarCode = (TextView)findViewById(R.id.tvbarcode);
//        tvStatus.setText("Balance : " +bugetAmount );
//        System.out.print("++____++++"+bugetAmount);
        HandleClick hc = new HandleClick();
        view = (ListView)findViewById(R.id.listView2);

        tvbarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataToServer();
//                finalBill();
            }
        });
        findViewById(R.id.butProd).setOnClickListener(hc);



        mArrayAdapter1 = new ArrayAdapter<Item>(getApplicationContext(),
                R.layout.mylistview, R.id.codeNumber, items);
        view.setAdapter(mArrayAdapter1);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);

                builder.setMessage("Confirm!!")
                        .setTitle("Are you sure you want to remove this item??");

                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        int i=0;
                        items.get(position).setQuantity(items.get(position).getQuantity()-1);
                        Item item = items.get(position);
                        for(Objects objects:newArray){

                            if(objects.getBarCode().equals(item.getBarcode()))
                            {
                              break;
                            }
                            i++;
                        }
                        newArray.remove(i);
                        mArrayAdapter1.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        dataFromServer();
        ///JSONArray jsonArray = new JSONArray(list);
        //JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,API_URL, new Response.Listener<j>(),)

        //finalBill();
    }

    public  void dataToServer(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BILL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String msg = "";
                        //Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject response_json = new JSONObject(response);
                            msg = response_json.get("message").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            Log.i("INFO", msg);
                            finalBill(msg.split(",")[1]);
                            newArray.clear();
//                        newArray.remove(position);
                        mArrayAdapter1.notifyDataSetChanged();
//                            finish();

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
                params.put("barcode_str", cancateBarcode.trim());
                params.put("customer_id",String.valueOf(customerId));
                params.put("final_bill",String.valueOf(totalAmount));

                //params.put("balance",String.valueOf(bugetAmount));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    public void finalBill(String purchaseId)
    {
        Intent finalBill = new Intent(getApplicationContext(),FinalBillAndItems.class);
        Bundle bundle = new Bundle();
        bundle.putString("purchaseId",purchaseId+"");
        bundle.putParcelableArrayList("arrayList",newArray);
        bundle.putString("amount",String.valueOf(totalAmount));
        finalBill.putExtras(bundle);
        startActivity(finalBill);
        newArray.clear();
        totalAmount = 0;
        mArrayAdapter1.notifyDataSetChanged();

    }
    public void dataFromServer(){


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, API_URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        try {

                                JSONArray jsonArray=response.getJSONArray("products");

                                for(int i = 0; i <jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                   barCode = jsonObject.getString("barcode");
                                   itemName = jsonObject.getString("name");
                                    String id = jsonObject.getString("id");

                                   itemPrice = jsonObject.getInt("price");
                                    arrayList.add(new Objects(barCode, itemName, itemPrice, id));

                                    Objects objects =arrayList.get(i);
                                    String barcodeTo = objects.getBarCode();
                                    String itemnameTo = objects.getItemName();
                                    int itempriceTo = objects.getItemPrice();




                                    System.out.print("\n---------"+barcodeTo+"++++++++"+itemnameTo+"___________"+itempriceTo+"\n");



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
                //params.put("phone", phoneNo);
                //params.put("password",Password);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
    private class HandleClick implements View.OnClickListener {
        public void onClick(View arg0) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            switch(arg0.getId()){

                case R.id.butProd:
                    intent.putExtra("SCAN_MODE", "PRODUCT_MODE");

                    break;

            }
            startActivityForResult(intent, 0);    //Barcode Scanner to scan for us
        }
    }
    public void onActivityResult(int requestCode, int resultCode, final Intent intent) {
        String productBarcode = intent.getStringExtra("SCAN_RESULT");
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                tvResult.setText(productBarcode);
                boolean found = false;

                for(int i=0;i<arrayList.size() && (!found);i++){


              //      for (String simpleList : list){
                        Objects objectList=arrayList.get(i);
                    Log.i("INFO",bugetAmount+"");
                    Log.i("LIST",objectList.getBarCode());
                        //bugetAmount -= objectList.getItemPrice();
                        //tvStatus.setText("Balance : " +bugetAmount);
                        if(objectList.getBarCode().equals(productBarcode)) {
                            if (true){
                                boolean itemFound = false;
                                for(Item item:items){
                                    if(item.getBarcode().equals(objectList.getBarCode())) {
//                                        item.setQuantity(item.getQuantity() + 1);
                                        itemFound = true;
                                        item.setQuantity(item.getQuantity()+1);
                                        break;
                                    }
                                }
                                if (!itemFound){
                                    Item item = new Item(objectList.getItemName(),objectList.getBarCode(),1);
                                    items.add(item);
                                }
                                bugetAmount -= objectList.getItemPrice();
//                                tvStatus.setText("Balance : " +bugetAmount);
                                totalAmount = totalAmount + objectList.getItemPrice();
                                cancateBarcode+=objectList.id+" ";
                                newArray.add(objectList);//""+objectList.getItemName()+""+objectList.getItemPrice());
                                mArrayAdapter1.notifyDataSetChanged();

                                found = true;
                            }
                            else
                            {
                                Toast.makeText(this, "Insufficient Amount ", Toast.LENGTH_SHORT).show();
                                break;
                            }

                        }


                    System.out.println("#####################"+totalAmount);

                }

                if(!found){

                        Toast.makeText(this, "This item is not available ", Toast.LENGTH_SHORT).show();


                }


            } else if (resultCode == RESULT_CANCELED) {
                tvStatus.setText("Press a button to start a scan.");
                tvResult.setText("Scan cancelled.");
            }
        }
    }



}
