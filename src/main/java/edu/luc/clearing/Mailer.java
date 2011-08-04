package edu.luc.clearing;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

public static void mail(String msgBody) throws Exception{

Date current = new Date();
@SuppressWarnings("deprecation")
String currentDate = "" + (current.getYear() + 1900) +"-"+ (current.getMonth()+1) +"-"+ current.getDate() + " " + current.getHours() + ":" + current.getMinutes() + ":" + current.getSeconds();

Properties props = new Properties();
Session session = Session.getDefaultInstance(props, null);

Message msg = new MimeMessage(session);
msg.setFrom(new InternetAddress("cbetheridge-cs370@appspot.com"));
msg.addRecipient(Message.RecipientType.TO, new InternetAddress("cbetheridge@gmail.com"));
msg.addRecipient(Message.RecipientType.TO, new InternetAddress("ndemcassius@gmail.com"));
msg.setSubject("CS370 - Errors - "+currentDate);
msg.setText(msgBody);
Transport.send(msg);
}

}


    
