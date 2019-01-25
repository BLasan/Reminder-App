package com.example.benura.myapplication;

import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailHelper extends  Login {

    public static void sendMail(String recipient, String subject, String content) {
        // config
        // Sender's email ID needs to be mentioned
        String from = "benuraab@gmail.com";

        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", "smtp.google.com");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.user", "benuraab@gmail.com");
        properties.setProperty("mail.password", "thiruniamanda@1997");
        //properties.put("mail.debug", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try{
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipient));

            message.setSubject(subject);
            message.setText(content);

            Transport trnsport;
            trnsport = session.getTransport("smtps");
            trnsport.connect(null, properties.getProperty("mail.password"));
            message.saveChanges();
            trnsport.sendMessage(message, message.getAllRecipients());
            trnsport.close();
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
