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
public class MessageError {
    public final static String BLANK_INPUT = "Semua kolom input wajib diisi.";
    public final static String INVALID_EMAIL_FORMAT = "Format email tidak valid.";
    public final static String INVALID_MINIMUM_PASSWORD = "Minimum 6 karakter untuk password.";
    public final static String INVALID_CONFIRM_PASSWORD = "Konfirmasi password anda tidak valid.";
    public final static String INVALID_OLD_PASSWORD = "Password yang dimasukkan tidak sesuai dengan password saat ini";
    public final static String INVALID_NEW_PASSWORD = "Password baru tidak boleh sama dengan password saat ini";
    public final static String INVALID_NUMERIC_TYPE = "Inputan harus berupa angka, yaitu kolom ";
    
    public final static String USER_NOT_FOUND = "Akun anda tidak ditemukan.";
    public final static String USER_PASSWORD_WRONG = "Password akun anda salah.";
    public final static String USER_ALREADY_EXIST = "Email sudah digunakan sebelumnya.";
    
    public final static String PRODUCT_NOT_FOUND = "Produk tidak ditemukan.";
    
    public final static String ORDER_NOT_FOUND = "Tidak ada data orderan yang dipilih.";
    
    public final static String OTP_CANCELED = "Validasi OTP dibatalkan.";
    public final static String OTP_INVALID = "Kode OTP tidak valid.";
    
    public final static String SESSION_INVALID = "Sesi akun tidak ada atau telah habis.";
    
    
    public static void displayErrorMessage(JFrame component, String message) {
        JOptionPane.showMessageDialog(component, message, "Terjadi Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
}
