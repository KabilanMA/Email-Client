import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ObjHandler {
    public static void serialize(JavaMail mail) {
        /**
         * This method accepts JavaMail object Serialize this JavaMail obj save into a
         * local file Doesn't return any attributes.
         */

        String path = (new File("").getAbsolutePath()) + "\\Sent_Mail";
        /**
         * create a new file instance to get the absolute path and set the path location
         * as a String
         */
        File directory = new File(path);
        /*
         * creating a new file obj to get the number of serialized object inside the
         * path location number of serialized obj is calculated only for purpose of
         * creating a new serilized obj name.
         */
        File f = new File(path);
        // creating a new file obj to create a folder incase folder doesn't exist in the
        // local system.
        int mailID;
        if (f.mkdir()) {
            mailID = 0;
        } else {
            mailID = directory.list().length;
        }
        String mailStr = "mailNO_";
        mailID++;
        mailStr += mailID;
        mailStr = path + "\\" + mailStr + ".ser";
        try {
            FileOutputStream file = new FileOutputStream(mailStr);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(mail);
            out.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deserialize(String mailDate) {
        /**
         * This static method accepts a String parameter of date when the mails were
         * send this method analyzes the file and deserialize and print the mails sent
         * on the particular date Doen't return any attributes
         */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate mailDATE = LocalDate.parse(mailDate, formatter); // get an instance of LocalDate to compare dates
        JavaMail mail = null; // declaring a JavaMail mail obj to derserialize the objects
        String path = (new File("").getAbsolutePath()) + "\\Sent_Mail"; // getting a string of the path to look for
                                                                        // serialized file
        File fileCounter = new File(path); // create a file obj to read the number of serialized object in the location
        int count = 0;
        int noOfMail = 0;
        boolean output = false; // to check whether any mail sent on the date
        try {
            count = fileCounter.list().length;
            for (int i = 1; i <= count; i++) {
                String mailStr = "\\mailNo_";
                mailStr = path + mailStr + i + ".ser"; // getting the path and name of the serialized object
                try {
                    FileInputStream file = new FileInputStream(mailStr);
                    ObjectInputStream in = new ObjectInputStream(file);
                    mail = (JavaMail) in.readObject(); // deserialization process
                    in.close();
                    file.close();
                    if ((mail.getDate()).compareTo(mailDATE) == 0) { // compare the mail sent date and input date
                        output = true;
                        noOfMail++;
                        System.out.println("Mail No " + noOfMail + " :\n" + "Recipient : " + mail.getRecipient() + "\n"
                                + "Subject : " + mail.getSubject() + "\n" + "Message : " + mail.getMessage() + "\n");
                    }
                } catch (IOException innerIO) {
                    innerIO.printStackTrace();
                } catch (ClassNotFoundException notFound) {
                }
            }
        } catch (NullPointerException e) {
            System.out.println("No File is available");
        }
        if (!output) {
            System.out.println("No mail sent on " + mailDate);
        }
    }

    public static ArrayList<JavaMail> deserialize() {
        /**
         * This static method doesn't accept any parameter.
         * this method deserialize only the Birthday mail object and put in an ArrayList
         * returns an ArrayList of JavaMail of birthday mail.
         */
        ArrayList<JavaMail> sendMails = new ArrayList<JavaMail>();
        LocalDate mailDate = LocalDate.now(); // get an instance of LocalDate to compare dates
        JavaMail mail = null; // declaring a JavaMail mail obj to derserialize the objects
        String path = (new File("").getAbsolutePath()) + "\\Sent_Mail"; // getting a string of the path to look for
                                                                        // serialized file
        File fileCounter = new File(path); // create a file obj to read the number of serialized object in the location
        int count = 0;
        try {
            count = fileCounter.list().length;
            for (int i = 1; i <= count; i++) {
                String mailStr = "\\mailNo_";
                mailStr = path + mailStr + i + ".ser"; // getting the path and name of the serialized object
                try {
                    FileInputStream file = new FileInputStream(mailStr);
                    ObjectInputStream in = new ObjectInputStream(file);
                    mail = (JavaMail) in.readObject(); // deserialization process
                    in.close();
                    file.close();
                    if ((mail.getDate()).compareTo(mailDate) == 0) { // compare the mail sent date and input date
                        sendMails.add(mail);
                    }
                } catch (IOException innerIO) {
                    innerIO.printStackTrace();
                } catch (ClassNotFoundException notFound) {
                }
            }
        } catch (NullPointerException e) {}
        return sendMails;
    }
}
