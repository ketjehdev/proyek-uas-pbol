package com.uaspbol.buddypet.tests;

// import java.util.Scanner;

import com.uaspbol.buddypet.controllers.AuthController;
import java.util.ArrayList;


// import com.uaspbol.buddypet.controllers.AuthController;

public class TestAuthLogin {
    public static void main(String[] args) {
        AuthController auth = new AuthController();
        
        ArrayList<String> credentials = new ArrayList<>();
        
        String email = "Neville";
        String password = "123456";
        
        credentials.add(email);
        credentials.add(password);
        
        auth.attempt(credentials, null);
    }
}
