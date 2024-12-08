/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaspbol.buddypet.controllers;

import com.uaspbol.buddypet.models.Product;
import com.uaspbol.buddypet.models.User;
import com.uaspbol.buddypet.utils.MessageError;
import com.uaspbol.buddypet.utils.MessageSuccess;
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
public class ProductController extends Controller {

    public void findAllProduct(JTable tableProduct) {
        try {
            query = "SELECT * FROM products";
            statement = conn.createStatement();
            result = statement.executeQuery(query);

            DefaultTableModel productTableModel = (DefaultTableModel) tableProduct.getModel();
            productTableModel.setRowCount(0);

            while (result.next()) {
                String productId = result.getString(1);
                String name = result.getString(2);
                String price = String.valueOf(result.getInt(3));
                String stockTotal = String.valueOf(result.getInt(4));
                String stockMinimum = String.valueOf(result.getInt(5));

                String[] products = {productId, name, price, stockTotal, stockMinimum};

                productTableModel.addRow(products);
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

    public void createProduct(Product product) {
        try {
            if(isBlankInput(product)) {
                throw new Exception(MessageError.BLANK_INPUT);
            }
            
            if(isInvalidNumeric(product)) {
                throw new Exception(MessageError.INVALID_NUMERIC_TYPE);
            }
            
            String productId = null;
            String productName = product.getProductName();
            String productPrice = product.getProductPrice();
            String productStockTotal = product.getProductStockTotal();
            String productStockMinimum = product.getProductStockMinimum();

            query = "SELECT COUNT(*) as totalProduct FROM products";
            statement = conn.createStatement();
            result = statement.executeQuery(query);

            if (result.next()) {
                Integer getProductId = result.getInt("totalProduct") + 1;
                productId = "PROD-" + getProductId.toString();
                statement.close();
            }

            query = "INSERT INTO products VALUES (?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, productId);
            preparedStatement.setString(2, productName);
            preparedStatement.setInt(3, Integer.parseInt(productPrice));
            preparedStatement.setInt(4, Integer.parseInt(productStockTotal));
            preparedStatement.setInt(5, Integer.parseInt(productStockMinimum));

            preparedStatement.executeUpdate();

            System.out.println(MessageSuccess.PRODUCT_CREATED);
            JOptionPane.showMessageDialog(null, MessageSuccess.PRODUCT_CREATED);

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

    public void updateProduct(String productId, Product product) {
        try {
            if(isBlankInput(product)) {
                throw new Exception(MessageError.BLANK_INPUT);
            }
            
            if(isInvalidNumeric(product)) {
                throw new Exception(MessageError.INVALID_NUMERIC_TYPE);
            }
            
            String productName = product.getProductName();
            String productPrice = product.getProductPrice();
            String productStockTotal = product.getProductStockTotal();
            String productStockMinimum = product.getProductStockMinimum();

            query = "UPDATE products SET name = ?, price = ?, stock_total = ?, stock_minimum = ? WHERE product_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, Integer.parseInt(productPrice));
            preparedStatement.setInt(3, Integer.parseInt(productStockTotal));
            preparedStatement.setInt(4, Integer.parseInt(productStockMinimum));
            preparedStatement.setString(5, productId);
            preparedStatement.executeUpdate();

            System.out.println(MessageSuccess.PRODUCT_UPDATED);
            MessageSuccess.displaySuccessMessage(null, MessageSuccess.PRODUCT_UPDATED);
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

    public void deleteProduct(String productId) {
        try {
            if (productId == null) {
                throw new Exception(MessageError.PRODUCT_NOT_FOUND);
            }

            query = "DELETE FROM products WHERE product_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, productId);
            preparedStatement.executeUpdate();

            System.out.println(MessageSuccess.PRODUCT_DELETED);
            MessageSuccess.displaySuccessMessage(null, MessageSuccess.PRODUCT_DELETED);
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

    private boolean isBlankInput(Product product) {
        return product.getProductName().isBlank() || product.getProductPrice().isBlank() || product.getProductStockTotal().isBlank() || product.getProductStockMinimum().isBlank();
    }
    
    public boolean isInvalidNumeric(Product product) {
        return !product.getProductPrice().matches("\\d+") || !product.getProductStockTotal().matches("\\d+") || !product.getProductStockMinimum().matches("\\d+");
    }
}
