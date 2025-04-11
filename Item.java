/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package databaseconnection;

/**
 *
 * @author CYBORG 15
 */

//read-only class to display data in table

public class Item {
    private String itemCode;
    private String itemName;
    private int stockQuantity;
    private double price;
    private String supplierCode;
    
    public Item(String itemCode, String itemName, int stockQuantity, double price, String supplierCode) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.supplierCode = supplierCode;
    }
    
    //getters
    public String getItemCode() { 
        return itemCode; 
    }
    
    public String getItemName() {
        return itemName; 
    }
    
    public int getStockQuantity() { 
        return stockQuantity;
    }
    
    public double getPrice() { 
        return price; 
    }
    
    public String getSupplierCode() {
        return supplierCode; 
    }
    
}
