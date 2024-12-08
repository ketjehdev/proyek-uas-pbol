/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaspbol.buddypet.models;

import java.time.LocalDate;

/**
 *
 * @author LENOVO
 */
public class Order {
    private String orderId;
    private String userId;
    private String productId;
    private Integer amountPurchased;
    private Integer amountPrice;
    private String orderStatus;
    private LocalDate orderDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getAmountPurchased() {
        return amountPurchased;
    }

    public void setAmountPurchased(Integer amountPurchased) {
        this.amountPurchased = amountPurchased;
    }

    public Integer getAmountPrice() {
        return amountPrice;
    }

    public void setAmountPrice(Integer amountPrice) {
        this.amountPrice = amountPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
