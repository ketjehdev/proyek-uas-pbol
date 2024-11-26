/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaspbol.buddypet.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 *
 * @author LENOVO
 */
public class Mail {
    private final String EMAIL_CONFIG       = "ketjeh.developer@gmail.com";
    private final String PASSWORD_CONFIG    = "uqgvmwmaelfwuxpt";
    private final String HOST_CONFIG        = "smtp.gmail.com";
    private final String PORT_CONFIG        = "465";
    
    public boolean sendEmail(String receiver, String subject, String bodyMessage) {
        Properties props = new Properties();
        props.put("mail.smtp.user", EMAIL_CONFIG);
        props.put("mail.smtp.host", HOST_CONFIG);
        props.put("mail.smpt.port", PORT_CONFIG);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", PORT_CONFIG);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        
        try {
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(bodyMessage);
            msg.setSubject(subject);
            msg.setFrom(new InternetAddress(EMAIL_CONFIG));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            msg.saveChanges();
            
            Transport transport = session.getTransport("smtp");
            transport.connect(HOST_CONFIG, EMAIL_CONFIG, PASSWORD_CONFIG);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            
            System.out.println("Email dengan subjek " + subject + " berhasil terkirim ke " + receiver);
            
            return true;
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
