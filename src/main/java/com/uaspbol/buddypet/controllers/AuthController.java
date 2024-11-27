package com.uaspbol.buddypet.controllers;

import com.uaspbol.buddypet.models.User;
import com.uaspbol.buddypet.views.LoginFrame;
import com.uaspbol.buddypet.views.RegisterFrame;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Random;

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
                String emailVerifiedAt = result.getString("email_verified_at");
                if(emailVerifiedAt == null) {
                    verifyOTP(email, component);
                }  else {
                    component.dispose();
                    redirectAfterAuthenticated();
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (component != null) {
                JOptionPane.showMessageDialog(component, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void register(User user, ArrayList<String> security, JFrame component) {
        try {
            String id = null;
            String email = user.getEmail();
            String password = user.getPassword();
            String confirmPassword = security.get(1);
            String role = user.getRole();
            String otp = null;
            
            // generate ID
            statement = conn.createStatement();
            query = "SELECT COUNT(*) as totalUser FROM users";
            result = statement.executeQuery(query);
            if(result.next()) {
                Integer totalUser = result.getInt("totalUser") + 1;
                user.setId("BP-"+totalUser.toString());
                id = user.getId();
            }
            
            // generate OTP
            Random rand = new Random();
            Integer otpGenerate = rand.nextInt(10000);
            if (otp == null) {
                user.setOtp(otpGenerate);
                otp = user.getOtp();
            }
            
            // validation
            query = "SELECT email FROM users WHERE email = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            result = preparedStatement.executeQuery();           
            
            if (result.next()) {
                throw new Exception("Email sudah digunakan sebelumnya!");
            }
            
            if(password.length() < 6) {
                throw new Exception("Minimum 8 karakter untuk password");
            }
            
            if(!password.equals(confirmPassword)) {
                throw new Exception("Konfirmasi password anda tidak valid");
            }

            // insert user data
            query = "INSERT INTO users VALUES(?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, role);
            preparedStatement.setString(5, otp);
            preparedStatement.executeQuery();
            
            System.out.println("Akun berhasil dibuat. Silakan login!");
            
            if(component != null) {
                component.dispose();
                JFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                JOptionPane.showMessageDialog(loginFrame, "Akun berhasil dibuat. Silakan login!");
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

    public void verifyOTP(String email, JFrame component) {
        try {
            query = "SELECT otp FROM users WHERE email = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            result = preparedStatement.executeQuery();
            
            if(!result.next()) {
                throw new Exception("Data user tidak ditemukan!");
            }
            
            boolean isNotValid = true;
            String otp = null;
            otp = result.getString("otp");
            
            while(isNotValid) {
                String otpValidation = JOptionPane.showInputDialog(null, "Masukkan Kode OTP:");
                
                if(otpValidation == null) {
                    throw new Exception("Validasi OTP dibatalkan.");
                }
                
                if(otpValidation.equals(otp)){
                    JOptionPane.showMessageDialog(null, "Kode OTP valid. Terimakasih");
                    
                    query = "UPDATE users SET email_verified_at = ? WHERE email = ?";
                    preparedStatement = conn.prepareStatement(query);
                    preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
                    preparedStatement.setString(2, email);
                    
                    preparedStatement.executeQuery();
                    
                    if(component != null) {
                        component.dispose();
                        redirectAfterAuthenticated();
                    }
                    
                    System.out.println("Kode OTP Valid. Berhasil diverifikasi!");
                    
                    isNotValid = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Kode OTP tidak valid", "Gagal", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(result!=null) result.close();
                if(preparedStatement!=null) preparedStatement.close();
                if(conn!=null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public JFrame redirectAfterAuthenticated() {
        JFrame registerFrame = new RegisterFrame();
        
        registerFrame.setVisible(true);
        
        return registerFrame;
    }
    
    // public void forgotPassword() {
    // }
    
// public void resetPassword() {
    // }
}
