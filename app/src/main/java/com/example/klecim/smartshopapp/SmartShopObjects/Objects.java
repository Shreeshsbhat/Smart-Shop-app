package com.example.klecim.smartshopapp.SmartShopObjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Arunkumar on 10/24/2016.
 */
public class Objects implements Parcelable{

    public String barCode,itemName;
    public int itemPrice;
    public String id;

    public String toString(){

        return "\n" + itemName + " \nPrice: " + itemPrice;
    }

    public Objects()
    {

    }

    public Objects(Parcel in) {
        itemName = in.readString();
        itemPrice = in.readInt();

    }
    public Objects(String barCode, String itemName, int itemPrice) {
        this.barCode = barCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
    public Objects(String barCode, String itemName, int itemPrice, String id) {
        this.barCode = barCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.id = id;
    }
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(itemName);
        dest.writeInt(itemPrice);


    }

    public static final Parcelable.Creator<Objects> CREATOR = new Parcelable.Creator<Objects>() {
        public Objects createFromParcel(Parcel in) {
            return new Objects(in);
        }

        public Objects[] newArray(int size) {
            return new Objects[size];

        }
    };

}

