/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaspbol.buddypet.controllers;

import com.uaspbol.buddypet.models.User;
import com.uaspbol.buddypet.utils.MessageError;
import com.uaspbol.buddypet.utils.MessageSuccess;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class ChangePasswordController extends Controller {

    public void updatePassword(String userId, ArrayList<String> password) {
        try {
            String oldPassword = password.get(0);
            String newPassword = password.get(1);
            String confirmNewPassword = password.get(2);

            if (userId == null) {
                throw new Exception(MessageError.SESSION_INVALID);
            }

            if (oldPassword.isBlank() || newPassword.isBlank() || confirmNewPassword.isBlank()) {
                throw new Exception(MessageError.BLANK_INPUT);
            }

            query = "SELECT password FROM users WHERE user_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, userId);
            result = preparedStatement.executeQuery();

            if (!result.next()) {
                throw new Exception(MessageError.USER_NOT_FOUND);
            }

            String dbPassword = result.getString("password");

            if (!oldPassword.equals(dbPassword)) {
                throw new Exception(MessageError.INVALID_OLD_PASSWORD);
            }

            if (newPassword.length() < 6) {
                throw new Exception(MessageError.INVALID_MINIMUM_PASSWORD);
            }

            if (newPassword.equals(oldPassword)) {
                throw new Exception(MessageError.INVALID_NEW_PASSWORD);
            }

            if (!newPassword.equals(confirmNewPassword)) {
                throw new Exception(MessageError.INVALID_CONFIRM_PASSWORD);
            }

            query = "UPDATE users SET password = ? WHERE user_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, userId);
            preparedStatement.executeUpdate();

            System.out.println(MessageSuccess.USER_PASSWORD_UPDATED);
            MessageSuccess.displaySuccessMessage(null, MessageSuccess.USER_PASSWORD_UPDATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            MessageError.displayErrorMessage(null, e.getMessage());
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
                MessageError.displayErrorMessage(null, e.getMessage());
            }
        }
    }
}
