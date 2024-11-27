/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.uaspbol.buddypet.tests;

import com.uaspbol.buddypet.controllers.AuthController;
import com.uaspbol.buddypet.models.User;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class TestAuthRegister {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AuthController auth = new AuthController();
        User user = new User();
        ArrayList<String> security = new ArrayList<>();
        
        // security setup
        String password = "123456";
        String confirmPassword = "123456";
        security.add(password);
        security.add(confirmPassword);
        
        // user data setup
        user.setEmail("laia@gmail.com");
        user.setPassword(password);
        user.setRole("customer");
         
        // action
        auth.register(user, security, null);
    }
    
}
