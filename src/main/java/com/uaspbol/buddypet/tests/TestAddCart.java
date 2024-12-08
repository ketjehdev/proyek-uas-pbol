/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.uaspbol.buddypet.tests;

import com.uaspbol.buddypet.controllers.OrderController;
import com.uaspbol.buddypet.models.Order;
import java.time.LocalDate;

/**
 *
 * @author LENOVO
 */
public class TestAddCart {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        OrderController order = new OrderController();
        Order model = new Order();
        
        model.setUserId("USR-1");
        model.setProductId("PRD-1");
        model.setAmountPurchased(5);
        model.setAmountPrice(5*200);
        model.setOrderStatus("KERANJANG");
        model.setOrderDate(LocalDate.now());
        
        order.addCart(model);
    }
    
}
