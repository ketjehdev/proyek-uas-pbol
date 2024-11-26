/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.uaspbol.buddypet.tests;

import com.uaspbol.buddypet.utils.Mail;

/**
 *
 * @author LENOVO
 */
public class TestMail {

    public static void main(String[] args) {
        Mail mail = new Mail();
        mail.sendEmail("nevillejro.laia@gmail.com", "Test Email - Java Swing", "Kembali dicoba!");
    }
    
}
