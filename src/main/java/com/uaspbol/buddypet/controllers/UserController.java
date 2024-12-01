/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaspbol.buddypet.controllers;

import com.uaspbol.buddypet.models.User;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class UserController extends Controller {
    public void findAllUser(JTable tableUser) {
        try {
            query = "SELECT * FROM users ORDER BY user_id ASC";
            statement = conn.createStatement();
            result = statement.executeQuery(query);

            while (result.next()) {
                String userId = result.getString("user_id");
                String email = result.getString("email");
                Date emailVerifiedAt = result.getDate("email_verified_at");
                String verifiedAt = String.valueOf(emailVerifiedAt);

                String users[] = {userId, email, verifiedAt};

                DefaultTableModel userTableModel = (DefaultTableModel) tableUser.getModel();

                userTableModel.addRow(users);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void createUser(User user) {
        try {
            String userId= null;
            String email = user.getEmail();
            String password = "bpet24";
            String role = user.getRole();
            
            query = "SELECT COUNT(*) as totalUser FROM users";
            statement = conn.createStatement();
            result = statement.executeQuery(query);
            
            if(result.next()) {
                Integer getUserId = result.getInt("totalUser") + 1;
                userId = "USR-" + getUserId.toString();
                statement.close();
            }
            
            if(email.isBlank()) {
                throw new Exception("Kolom input wajib diisi!");
            }
            
            query = "INSERT INTO users(user_id, email, password, email_verified_at, role) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(5, role);
            
            result = preparedStatement.executeQuery();
            
            System.out.println("User berhasil dibuat!");
            JOptionPane.showMessageDialog(null, "User berhasil dibuat!");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                result.close();
                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage(), "Server Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void updateUser(String userId, User user) {
        try {
            String email = user.getEmail();
            String role = user.getRole();
            
            if(email.isBlank() || role.isBlank()) {
                throw new Exception("Kolom input wajib diisi");
            }
            
            query = "UPDATE users SET email = ?, role = ? WHERE user_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, role);
            preparedStatement.setString(3, userId);

            result = preparedStatement.executeQuery();
            
            if(!result.next()) {
                throw  new Exception("Tidak dapat memperbarui user");
            }
            
            System.out.println("Berhasil memperbarui user");
            JOptionPane.showMessageDialog(null, "Berhasil memperbarui user");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(result!=null) result.close();
                if(preparedStatement!=null) preparedStatement.close();
                if(conn!=null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage(), "Server Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void deleteUser (String userId) {
        try {
            query = "DELETE FROM users WHERE user_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, userId);
            result = preparedStatement.executeQuery();
            
            if(!result.next()) {
                throw new Exception("Tidak dapat menghapus user");
            }
            
            System.out.println("User telah dihapus");
            JOptionPane.showMessageDialog(null, "User telah dihapus");
        } catch (Exception e) {
        } finally {
            try {
                if(result!=null) result.close();
                if(preparedStatement!=null) preparedStatement.close();
                if(conn!=null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage(), "Server Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
}
