/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaspbol.buddypet.models;

/**
 *
 * @author LENOVO
 */
public class Product {
    private String productId;
    private String productName;
    private String productPrice;
    private String productStockTotal;
    private String productStockMinimum;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductStockTotal() {
        return productStockTotal;
    }

    public void setProductStockTotal(String productStockTotal) {
        this.productStockTotal = productStockTotal;
    }

    public String getProductStockMinimum() {
        return productStockMinimum;
    }

    public void setProductStockMinimum(String productStockMinimum) {
        this.productStockMinimum = productStockMinimum;
    }
}
