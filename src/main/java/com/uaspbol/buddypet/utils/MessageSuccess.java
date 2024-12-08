/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaspbol.buddypet.utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class MessageSuccess {
    public final static String USER_FOUND = "Akun anda ditemukan."; 
    public final static String USER_CREATED = "Akun berhasil dibuat.";
    public final static String USER_UPDATED = "Berhasil memperbarui akun.";
    public final static String USER_DELETED = "Akun telah berhasil dihapus";
    
    public final static String PRODUCT_CREATED = "Produk berhasil ditambahkan.";
    public final static String PRODUCT_UPDATED = "Produk berhasil diperbarui.";
    public final static String PRODUCT_DELETED = "Produk telah berhasil dihapus.";
    
    public final static String TRANSACTION_SUCCESS = "Berhasil membeli dan membayar produk.";
    
    public final static String CART_DELETED = "Berhasil menghapus data keranjang.";
    
    public final static String USER_PASSWORD_UPDATED = "Password anda berhasil diperbarui.";
    
    public final static String OTP_VALIDATED = "Kode OTP Valid. Berhasil diverifikasi!";
    
    public static void displaySuccessMessage(JFrame component, String message) {
        JOptionPane.showMessageDialog(component, message);
    }
}
