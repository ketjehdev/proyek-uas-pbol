package com.uaspbol.buddypet.controllers;

import com.uaspbol.buddypet.views.RegisterFrame;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AuthController extends Controller {

    public void attempt(ArrayList<String> credentials, JFrame component) {
        try {
            String email = credentials.get(0);
            String password = credentials.get(1);

            if (email.equals("") || password.equals("")) {
                throw new Exception("Kolom input wajib diisi buddy!");
            }

            query = "SELECT * FROM users WHERE email = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
           
            result = preparedStatement.executeQuery();

            if (!result.next()) {
                throw new Exception("Akun anda tidak ditemukan buddy:(");
            }

            String tempPassword = result.getString("password");
            if (!tempPassword.equals(password)) {
                throw new Exception("Password anda salah buddy!");
            }

            System.out.println("Akun ditemukan buddy!!");
            if (component != null) {
                component.dispose();
                new RegisterFrame().setVisible(true);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (component != null) {
                JOptionPane.showMessageDialog(component, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            try {
                if(result != null) result.close();
                if(preparedStatement != null) preparedStatement.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // public void register() {
    // }
    // public void verifyOTP() {
    // }
    // public void forgotPassword() {
    // }
    // public void resetPassword() {
    // }
}
