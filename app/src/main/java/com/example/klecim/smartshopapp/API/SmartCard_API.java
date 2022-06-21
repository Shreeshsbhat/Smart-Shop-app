package com.example.klecim.smartshopapp.API;

/**
 * Created by Arunkumar on 10/7/2016.
 */
public class SmartCard_API {

    public static  final String BASE_URL = "http://192.168.1.104:5000/smartshop";
    //Admin Table Api
    public static final String LOGIN_URL =BASE_URL +"/api/customer/verify.php";
    public static final String REGISTER_URL = BASE_URL+"/api/customer/create.php";


    public static final String ITEM_URL = BASE_URL+"/api/product/read.php";
    public static final String FINALIZE_BILL_URL = BASE_URL+"/api/product/api_finalize_purchase.php";

    public static final String BILL_URL = BASE_URL+"/api/product/purchase.php";

    //public static final String LOGIN_URL ="http://192.168.101.1:80/SmartBarcode/public/index.php/new_login";

    //public static final String REGISTER_URL ="http://192.168.101.1:80/SmartBarcode/public/index.php/new_register";


}
