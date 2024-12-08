/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaspbol.buddypet.controllers;

import com.uaspbol.buddypet.models.Transaction;
import com.uaspbol.buddypet.utils.MessageError;
import com.uaspbol.buddypet.utils.MessageSuccess;
import java.sql.Date;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class TransactionController extends Controller {
    public void findAllTransaction(JTable tableTransaction) {
        try {
            query = "SELECT * FROM transactions";
            statement = conn.createStatement();
            result = statement.executeQuery(query);

            DefaultTableModel transactionTableModel = (DefaultTableModel) tableTransaction.getModel();
            transactionTableModel.setRowCount(0);
            
            while (result.next()) {
                String transactionId = result.getString("transaction_id");
                String orderId = result.getString("order_id");
                String paymentChannel = result.getString("payment_channel");
                String transactionStatus = result.getString("transaction_status");
                String transactions[] = {transactionId, orderId, paymentChannel, transactionStatus};
                transactionTableModel.addRow(transactions);
            }
            
            tableTransaction.repaint();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
                System.out.println(e.getMessage());
                MessageError.displayErrorMessage(null, e.getMessage());
            }
        }
    }
    
    public void createTransaction(Transaction transaction, String productId) {
        try {
            String transactionId = null;
            String orderId = transaction.getOrderId();
            String paymentChannel = transaction.getPaymentChannel();
            String transactionStatus = "DIBAYAR";

            if(productId == null || orderId == null) {
                throw new Exception("Pastikan anda memilih item yang akan dibayar.");
            }
            
            query = "SELECT COUNT(*) as totalTransaction FROM transactions";
            statement = conn.createStatement();
            result = statement.executeQuery(query);

            if (result.next()) {
                Integer getTransactionId = result.getInt("totalTransaction") + 1;
                transactionId = "PROD-" + getTransactionId.toString();
                statement.close();
            }
            
            query = "SELECT * FROM products WHERE product_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, productId);
            result = preparedStatement.executeQuery();
            
            if(!result.next()) {
                throw new Exception(MessageError.PRODUCT_NOT_FOUND);
            }
            
            Integer currentStock = result.getInt("stock_total");
            Integer minimumStock = result.getInt("stock_minimum");
            
            Integer newStock = currentStock - 1;
            query = "UPDATE products SET stock_total = ? WHERE product_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, newStock);
            preparedStatement.setString(2, productId);
            preparedStatement.executeUpdate();
            
            if(newStock <= minimumStock) {
                String notifId = "NOTIF-" + String.valueOf((int) (Math.random() * 1000));
                query = "INSERT INTO notifications VALUES(?, ?, ?)";
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, notifId);
                preparedStatement.setString(2, "Stok bersisa " + newStock);
                preparedStatement.setString(3, "Pastikan anda menambah stok agar tidak kekurangan.");
                preparedStatement.executeUpdate();
            }

            query = "INSERT INTO transactions VALUES (?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, transactionId);
            preparedStatement.setString(2, orderId);
            preparedStatement.setString(3, paymentChannel);
            preparedStatement.setString(4, transactionStatus);

            preparedStatement.executeUpdate();

            System.out.println(MessageSuccess.TRANSACTION_SUCCESS);
            JOptionPane.showMessageDialog(null, MessageSuccess.TRANSACTION_SUCCESS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            MessageError.displayErrorMessage(null, e.getMessage());
        } finally {
            try {
                result.close();
                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                MessageError.displayErrorMessage(null, e.getMessage());
            }
        }
    }
}
