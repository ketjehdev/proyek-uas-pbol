package com.uaspbol.buddypet.tests;

// import java.util.Scanner;

import com.uaspbol.buddypet.controllers.AuthController;
import com.uaspbol.buddypet.models.User;


// import com.uaspbol.buddypet.controllers.AuthController;

public class TestAuthLogin {
    public static void main(String[] args) {
        AuthController auth = new AuthController();
        
        User credentials = new User();
        
        String email = "Neville";
        String password = "123456";
        
        credentials.setEmail(email);
        credentials.setPassword(password);
        
        auth.attempt(credentials, null);
    }
}
