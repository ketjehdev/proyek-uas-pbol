/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.uaspbol.buddypet.tests;

import com.uaspbol.buddypet.controllers.AuthController;

/**
 *
 * @author LENOVO
 */
public class TestAuthVerifyOTP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AuthController auth = new AuthController();
        
        auth.verifyOTP("neville@gmail.com", null);
    }
    
}
