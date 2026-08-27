package com.digitusforum.user.emailVerification;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailVerificationSender {

    // Replace sender@example.com with your "From" address.
    // This address must be verified.
    static final String FROM = "eusouprogramadorjava@gmail.com";
    static final String FROMNAME = "Ricardo Didata";
	
   
    
    // Replace smtp_username with your Amazon SES SMTP user name.
    static final String SMTP_USERNAME = System.getenv("AWS_SES_SMTP_USERNAME");
    
    // Replace smtp_password with your Amazon SES SMTP password.
    static final String SMTP_PASSWORD = System.getenv("AWS_SES_SMTP_PASSWORD");
    
    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
    //static final String CONFIGSET = "ConfigSet";
    
    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
    // for more information.    
    static final String HOST = "email-smtp.us-east-1.amazonaws.com";
    
    // The port you will connect to on the Amazon SES SMTP endpoint. 
    static final int PORT = 587;
    
    //static final String SUBJECT = "of course i still love you";
    
    public static void sendValidationEmailAsinc(String email, int verificationCode) {
    	new Thread(() -> {
    		String body = String.join(
            	    System.getProperty("line.separator"),
            	    "<h1>Bem vindo ao mundo java</h1>",
            	    "<p>Você está a um passo de trabalhar de onde quiser, com salários incríveis e nunca mais ficar sem emprego.</p>", 
            	    "<p>Seu código de verificação é "+ verificationCode +"</p>", 
            	    "<p>Clique aqui para verificar sua conta www.eusouprogramadorjava.com/html/validateEmail.html?&email="+email+"&validationCode="+verificationCode+"</p>"
            	);
			send(email, verificationCode, body, "Seja muito bem vindo | Eu sou programador java");
		}).start();
    }
    
    public static void sendResetPasswordEmailAsinc(String email, int verificationCode) {
    	new Thread(() -> {
    		String body = String.join(
            	    System.getProperty("line.separator"),
            	    "<h1>Redefinição de senha</h1>",
            	    "<p>Recebemos uma solicitação para redefinir sua senha na nossa plataforma.</p>", 
            	    "<p>Seu código é "+ verificationCode +"</p>", 
            	    "<p>Clique aqui para redefinir sua senha www.eusouprogramadorjava.com/html/resetPassword.html?&email="+email+"&validationCode="+verificationCode+"</p>"
            	);
			send(email, verificationCode, body, "Redefinição de senha | Eu sou programador java");
		}).start();
    }
    

    private static void send(String email, int verificationCode, String body, String subject)  {

    	 // Replace recipient@example.com with a "To" address. If your account 
        // is still in the sandbox, this address must be verified.
        String TO = email;
    	
    	
    	
        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

    	
    	Transport transport = null;
        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);
    	 try
         {
        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(subject);
        msg.setContent(body,"text/html");
        
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
        //msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        // Create a transport.
        transport = session.getTransport();
                    
        // Send the message.
       
            System.out.println("Sending...");
            
            if (SMTP_USERNAME == null || SMTP_PASSWORD == null) {
                System.out.println("SES SMTP env vars missing");
                return;
            }
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            //transport.connect(SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            try {
				transport.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
