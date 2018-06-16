package com.jeff.clients.email;

import com.jeff.application.configuration.ConfigConstants;
import com.jeff.clients.Client;
import com.jeff.clients.reddit.ChildData;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailClient implements Client {

    private Properties props;

    public EmailClient(){
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

    }

    public void send(String address, String subject, String body) {

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(ConfigConstants.EMAIL, ConfigConstants.PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ConfigConstants.EMAIL));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getAllAddresses() {
        PhoneNumber[] phoneNumbers = EmailConstants.PHONE_NUMBER_LIST.getPhoneNumbers();
        StringBuilder stringBuilder = new StringBuilder();
        for (PhoneNumber phoneNumber : phoneNumbers) {
            stringBuilder.append(phoneNumber.getNumber());

            switch (phoneNumber.getCarrier()) {
                case TMOBILE:
                    stringBuilder.append("@tmomail.net,");
                    break;
                case VERIZON:
                    stringBuilder.append("@vtext.com,");
                    break;
                case ATT:
                    stringBuilder.append("@txt.att.net,");
                    break;
                case METROPCS:
                    stringBuilder.append("@mymetropcs.com,");
                    break;
                case SPRINT:
                    stringBuilder.append("@messaging.sprintpcs.com,");
                    break;
                case NONE:
                default:
                    stringBuilder.append(",");
            }
        }

        return stringBuilder.toString();
    }

    public void sendNotifications(HashMap<String, ChildData> newPosts) {

            for (Map.Entry<String, ChildData> entry : newPosts.entrySet()) {
                System.out.println("Found new Run: " + entry.getValue().getTitle() + " " + entry.getValue().getURL());
                send(getAllAddresses(),
                        entry.getValue().getTitle() + ": " + entry.getValue().getNum_comments(), entry.getValue().getURL());
            }

    }

    public void sendNotification(Map.Entry<String, ChildData> entry){
        System.out.println("Found new Run: " + entry.getValue().getTitle() + " " + entry.getValue().getURL());
        send(getAllAddresses(),
                entry.getValue().getTitle() + ": " + entry.getValue().getNum_comments(), entry.getValue().getURL());
    }
}
