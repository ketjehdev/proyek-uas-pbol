package com.uaspbol.buddypet.controllers;

import com.uaspbol.buddypet.models.User;
import com.uaspbol.buddypet.utils.Mail;
import com.uaspbol.buddypet.utils.MessageError;
import com.uaspbol.buddypet.utils.MessageSuccess;
import com.uaspbol.buddypet.utils.SessionLogin;
import com.uaspbol.buddypet.views.AdminFrame;
import com.uaspbol.buddypet.views.CustomerFrame;
import com.uaspbol.buddypet.views.LoginFrame;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Random;

public class AuthController extends Controller {

    public void attempt(User credentials, JFrame component) {
        try {
            String email = credentials.getEmail();
            String password = credentials.getPassword();

            if (email.isBlank() || password.isBlank()) {
                throw new Exception(MessageError.BLANK_INPUT);
            }
            
            if(!email.contains("@")) {
                throw new Exception(MessageError.INVALID_EMAIL_FORMAT);
            }

            query = "SELECT * FROM users WHERE email = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);

            result = preparedStatement.executeQuery();

            if (!result.next()) {
                throw new Exception(MessageError.USER_NOT_FOUND);
            }
            
            String tempPassword = result.getString("password");
            if (!tempPassword.equals(password)) {
                throw new Exception(MessageError.USER_PASSWORD_WRONG);
            }
            
            SessionLogin.setUserId(result.getString("user_id"));

            System.out.println(MessageSuccess.USER_FOUND);
            String role = result.getString("role");
            
            if (component != null) {
                String emailVerifiedAt = result.getString("email_verified_at");
                if(emailVerifiedAt == null) {
                    verifyOTP(email, component);
                }  else {
                    component.dispose();
                    redirectAfterAuthenticated(role);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (component != null) {
                MessageError.displayErrorMessage(component, e.getMessage());
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
                MessageError.displayErrorMessage(component, e.getMessage());
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
            
            statement = conn.createStatement();
            query = "SELECT COUNT(*) as totalUser FROM users";
            result = statement.executeQuery(query);
            if(result.next()) {
                Integer totalUser = result.getInt("totalUser") + 1;
                user.setId("BP-"+totalUser.toString());
                id = user.getId();
            }
            
            Random rand = new Random();
            Integer otpGenerate = rand.nextInt(10000);
            if (otp == null) {
                user.setOtp(otpGenerate);
                otp = user.getOtp();
            }
            
            if(email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                throw new Exception(MessageError.BLANK_INPUT);
            }
            
            if(!email.contains("@")) {
                throw new Exception(MessageError.INVALID_EMAIL_FORMAT);
            }
            
            query = "SELECT email FROM users WHERE email = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            result = preparedStatement.executeQuery();           
            
            if (result.next()) {
                throw new Exception(MessageError.USER_ALREADY_EXIST);
            }
            
            if(password.length() < 6) {
                throw new Exception(MessageError.INVALID_MINIMUM_PASSWORD);
            }
            
            if(!password.equals(confirmPassword)) {
                throw new Exception(MessageError.INVALID_CONFIRM_PASSWORD);
            }
            
            query = "INSERT INTO users VALUES(?, ?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, otp);
            preparedStatement.setString(5, role);
            preparedStatement.setDate(6, null);
            preparedStatement.executeUpdate();
            
            Mail mail = new Mail();
            mail.sendEmail(email, "Kode OTP BuddyPet", otp);
            System.out.println(MessageSuccess.USER_CREATED);
            
            if(component != null) {
                component.dispose();
                JFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                MessageSuccess.displaySuccessMessage(loginFrame, MessageSuccess.USER_CREATED);
            } 
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (component != null) {
                MessageError.displayErrorMessage(component, e.getMessage());
            }
        } finally {
            try {
//                conn.commit();
                if(result != null) result.close();
                if(preparedStatement != null) preparedStatement.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                MessageError.displayErrorMessage(component, e.getMessage());
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
                throw new Exception(MessageError.USER_NOT_FOUND);
            }
            
            boolean isNotValid = true;
            String otp = null;
            otp = result.getString("otp");
            
            while(isNotValid) {
                String otpValidation = JOptionPane.showInputDialog(null, "Masukkan Kode OTP:");
                
                if(otpValidation == null) {
                    throw new Exception(MessageError.OTP_CANCELED);
                }
                System.out.println(otpValidation);
                System.out.println(otp);
                if(otpValidation.equals(otp)){
                    MessageSuccess.displaySuccessMessage(null, MessageSuccess.OTP_VALIDATED);
                    
                    query = "UPDATE users SET email_verified_at = ? WHERE email = ?";
                    preparedStatement = conn.prepareStatement(query);
                    preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
                    preparedStatement.setString(2, email);
                    preparedStatement.executeUpdate();
                    
                    query = "SELECT role FROM users WHERE email = ?";
                    preparedStatement = conn.prepareStatement(query);
                    preparedStatement.setString(1, email);
                    result = preparedStatement.executeQuery();
                    
                    if(!result.next()) {
                        throw new Exception(MessageError.USER_NOT_FOUND);
                    }
                    
                    String role = result.getString("role");
                    
                    if(component != null) {
                        component.dispose();
                        redirectAfterAuthenticated(role);
                    }
                    
                    System.out.println(MessageSuccess.OTP_VALIDATED);
                    MessageSuccess.displaySuccessMessage(null, MessageSuccess.OTP_VALIDATED);
                    
                    isNotValid = false;
                } else {
                    MessageError.displayErrorMessage(null, MessageError.OTP_INVALID);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            MessageError.displayErrorMessage(null, e.getMessage());
        } finally {
            try {
//                conn.commit();
                if(result!=null) result.close();
                if(preparedStatement!=null) preparedStatement.close();
                if(conn!=null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                MessageError.displayErrorMessage(null, e.getMessage());
            }
        }
    }
    
    public JFrame redirectAfterAuthenticated(String role) {
        JFrame adminFrame = new AdminFrame();
        JFrame custFrame = new CustomerFrame();
        
        if(role.equals("admin")) {
            adminFrame.setVisible(true);
            return adminFrame;
        } 
            
        custFrame.setVisible(true);
        return custFrame;
    }
    
    public void logout(JFrame component) {
        component.dispose();
        new LoginFrame().setVisible(true);
        
        SessionLogin.setUserId(null);
        JOptionPane.showMessageDialog(null, "Anda telah keluar. Sesi telah habis");
    }
    
   // public void forgotPassword() {
   // }
    
    // public void resetPassword() {
    // }
}
