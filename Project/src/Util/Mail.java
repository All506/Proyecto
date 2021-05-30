/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author juanp
 */
public class Mail {

    public Mail() {

    }

    public static void enviarConGMail(String recepient, String[] data, String reason) throws Exception {
        System.out.println("Preparando para mandar el correo");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);

        //Cuenta desde donde mandamos el correo
        String myAccountEmail = "noreplyucr1@gmail.com";
        String password = "ucr12345!";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {//Aunteticacion de la cuenta gmail
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        switch (reason) {
            case "register":
                Message message = sendEmailRegister(session, myAccountEmail, recepient, data);
                Transport.send(message);
                System.out.println("Correo Registrar listo");
                break;
            case "deEnrollment":
                //Message message1 = sendEmailDeEnrollment(session, myAccountEmail, recepient, data);
                //Transport.send(message1);
                System.out.println("Correo Retiro listo");
                break;
            case "unRegister":
        }

    }

    private static Message sendEmailRegister(Session session, String myAccountEmail, String recepient, String[] data) throws IOException {
        String[] dataName = {"Student Identification", "Identification", "Carrer Identification", "Last Name", "First Name", "Phone Number", "Email", "Address", "Birthday"};
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));

            //MultiPart
            Multipart emailContent = new MimeMultipart();

            //Body 
            MimeBodyPart mimeBody = new MimeBodyPart();
            String text = "Your information about your registration: \n";
            text += "\n";
            for (int i = 0; i < dataName.length; i++) {
                text += (dataName[i] + ": " + data[i] + "\n");
            }
            text += "\nYour User is: " + data[1] + " and your Password is: " + data[0];
            mimeBody.setText(text);

            //Image Attachment
            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile("src/img/logo-ucr.png");

            emailContent.addBodyPart(pdfAttachment);
            emailContent.addBodyPart(mimeBody);

            //Agregamos al mensaje el contenido a mandar
            message.setSubject("Universidad de Costa Rica");
            message.setContent(emailContent);

            return message;
        } catch (MessagingException me) {
            System.out.println(me);
        }
        return null;
    }

    private static Message sendEmailDeEnrollment(Session session, String myAccountEmail, String recepient, String[] data) {
        return null;
    }

}//end class
