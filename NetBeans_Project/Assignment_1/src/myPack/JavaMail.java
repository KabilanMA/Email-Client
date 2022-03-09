package myPack;

import java.io.*;
import java.time.LocalDate;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMail implements Serializable {
    /**
     * This class implements Serializable interface to support serilization for the
     * instnace of this class
     */
    private final String recipient;
    private String subject;
    private String msg;
    private LocalDate today;
    private static final long serialVersionUID = 1L;

    public JavaMail(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return msg;
    }

    public LocalDate getDate() {
        return today;
    }

    public void sendMail(String subject, String msg) throws Exception {
        this.subject = subject;
        this.msg = msg;
        this.today = LocalDate.now();
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        // below code is only for the gmail account.
        // every server has its own smtp host address and port number to access it.
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // This is a sample Gmail account created specifically for this purpose
        // This is the mainID of the user using it
        String myEmail = "javatest190283@gmail.com";
        String password = "CSE@190283c#";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });

        Message message = prepareMessage(session, myEmail, recipient, subject, msg);
        try {
            Transport.send(message);
            System.out.println("Message Send Successfully!");
            ObjHandler.serialize(this);
        } catch (MessagingException e) {
            System.out.println("Check Your Network or Gmail Address");
        }
    }

    private Message prepareMessage(Session session, String myEmail, String recipient, String subject, String msg) {
        try {
            msg += "\n\nBest Regards,\n--\nM KABILAN,\nUndergraduate,\nDepartment of Computer Science & Engineering,\nUniversity of Moratuwa.";
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, (new InternetAddress(recipient)));
            message.setSubject(subject);
            message.setText(msg);
            return message;
        } catch (MessagingException e) {
            System.out.println("Check your network");
            Logger.getLogger(JavaMail.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}