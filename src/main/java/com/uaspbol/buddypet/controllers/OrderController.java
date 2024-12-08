/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaspbol.buddypet.controllers;

import com.uaspbol.buddypet.models.Order;
import com.uaspbol.buddypet.utils.MessageError;
import com.uaspbol.buddypet.utils.MessageSuccess;
import com.uaspbol.buddypet.utils.SessionLogin;
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
public class OrderController extends Controller {

    public void findAllCart(JTable tableCart) {
        try {
            String userId = SessionLogin.getUserId();
            query = "SELECT * FROM orders o "
                    + "JOIN products p ON o.product_id = p.product_id "
                    + "WHERE user_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, userId);
            result = preparedStatement.executeQuery();

            DefaultTableModel cartTableModel = (DefaultTableModel) tableCart.getModel();
            cartTableModel.setRowCount(0);

            while (result.next()) {
                String orderId = result.getString("order_id");
                String productId = result.getString("product_id");
                String productName = result.getString("name");
                String amountPurchased = String.valueOf(result.getInt("amount_purchased"));
                String amountPrice = String.valueOf(result.getInt("amount_price"));
                String productStockTotal = result.getString("stock_total");

                String[] products = {orderId, productId, productName, amountPurchased, amountPrice, productStockTotal};
                cartTableModel.addRow(products);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            MessageError.displayErrorMessage(null, e.getMessage());
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
                System.out.println("Closing Error: " + e.getMessage());
                MessageError.displayErrorMessage(null, e.getMessage());
            }
        }
    }

    public void addCart(Order order) {
        try {
            String orderId = null;
            query = "SELECT COUNT(*) as totalOrder FROM orders";
            statement = conn.createStatement();
            result = statement.executeQuery(query);

            if (result.next()) {
                Integer getOrderId = result.getInt("totalOrder") + 1;
                order.setOrderId("ORD-" + getOrderId);
                orderId = order.getOrderId();
                statement.close();
            }

            String userId = order.getUserId();
            String productId = order.getProductId();
            String amountPurchasedString = String.valueOf(order.getAmountPurchased());

            if (productId.isBlank() || amountPurchasedString.isBlank()) {
                throw new Exception(MessageError.BLANK_INPUT);
            }

            if (!amountPurchasedString.matches("\\d+")) {
                throw new Exception(MessageError.INVALID_NUMERIC_TYPE);
            }

            Integer amountPurchased = order.getAmountPurchased();

            if (amountPurchased == 0) {
                throw new Exception("Jumlah dibeli harus lebih dari 0");
            }

            Integer amountPrice = order.getAmountPrice();

            String orderStatus = "KERANJANG";
            LocalDate orderDate = order.getOrderDate();

            query = "INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, orderId);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, productId);
            preparedStatement.setInt(4, amountPurchased);
            preparedStatement.setInt(5, amountPrice);
            preparedStatement.setString(6, orderStatus);
            preparedStatement.setDate(7, Date.valueOf(orderDate));
            preparedStatement.executeUpdate();

            System.out.println("Berhasil ditambahkan ke keranjang anda");
            JOptionPane.showMessageDialog(null, "Berhasil ditambahkan ke keranjang anda!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "Server Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deleteCart(String orderId) {
        try {
            if (orderId == null) {
                throw new Exception(MessageError.PRODUCT_NOT_FOUND);
            }

            query = "DELETE FROM orders WHERE order_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, orderId);
            preparedStatement.executeUpdate();

            System.out.println(MessageSuccess.CART_DELETED);
            MessageSuccess.displaySuccessMessage(null, MessageSuccess.CART_DELETED);
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
