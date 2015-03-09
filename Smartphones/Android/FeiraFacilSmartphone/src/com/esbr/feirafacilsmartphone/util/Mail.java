package com.esbr.feirafacilsmartphone.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.esbr.feirafacilsmartphone.supermercado.Carrinho;





public class Mail  { 
	private final SimpleDateFormat format = new SimpleDateFormat(
            "HH:mm.ss - dd/MM/yyyy");
    private Session session;
    private Properties properties;
    private static Mail instance = null;
    private String emailBody;
    private static final String email = "test.supermercadoideal@gmail.com";
    private static final String subject = "Supermercado Ideal - Pedido";
    private static final String password = "ideal123";

    /**
     * Initiate session.
     */
    protected Mail() {
        session = starProperties();
    }

    /**
     * Method that returns an Instance of Mail.
     * 
     * @return - A Mail instance
     */
    public static Mail getInstance() {
        if (instance == null) {
            instance = new Mail();
        }
        return instance;
    }

    
    public Message buildContent(final Carrinho carrinho, String emailTO, String name)
            throws UnsupportedEncodingException, MessagingException {
        emailBody = contructEmailBody(carrinho,name);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("test.supermercadoideal", "Supermercado Ideal"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                emailTO));
        message.setSubject(subject);
        message.setText(emailBody);
        
        

        return message;
    }

    public String contructEmailBody(final Carrinho carrinho,String name) {
        String asw = "Caro senhor "+ name+", \n\n"+
        		"Recebemos sua solicitação de compra. Estamos analisando seu pedido e entraremos em contato.\n\n"+	
        		carrinho.toString()
        		+"\nAtenciosamente\n"
        		+"Equipe Supermercado Ideal";
                
       
        return asw;

    }

    
    public Session getSession() {
        return session;
    }

    public String getSubject() {
        return subject;
    }

    
    public String getEmail() {
        return email;
    }

    
    private Session starProperties() {
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
    }
	  
}
