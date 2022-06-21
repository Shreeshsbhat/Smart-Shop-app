package com.example.klecim.smartshopapp.SmartShopObjects;

public class Item {
    private String barcode, name;
    private int quantity;

    public Item(String name,String barcode, int quantity) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return name + "\n" + "Quantity: "+quantity;
    }
}
