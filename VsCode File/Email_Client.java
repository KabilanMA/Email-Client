//190283C

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Don't try to add recipient manually if you do so delete the clientList.txt and add the recipients from the commandline.
public class Email_Client {
    static int recipientCount;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please wait for a movement");
        System.out.println();
        ArrayList<Recipient> recipientObjList = getRecipient();
        if (recipientObjList == null) {
            recipientCount = 0;
        } else {
            recipientCount = recipientObjList.size();
        }
        sendBirthdayMail(recipientObjList); // send a mail to recipient who have birthday today.
        System.out.println();
        System.out.println("Thank you for waiting");
        System.out.println();
        System.out.println("Enter option type: \n" + "1 - Adding a new recipient\n" + "2 - Sending an email\n"
                + "3 - Printing out all the recipients who have birthdays\n"
                + "4 - Printing out details of all the emails sent\n"
                + "5 - Printing out the number of recipient objects in the applicaiton");
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                System.out.print("Enter the Recipient Details : ");
                scanner.nextLine();
                String detail = scanner.nextLine();
                String type = detail.split(":")[0].trim();
                detail = detail.split(":")[1].trim();
                addRecipient(type, detail); // add recipient to the clientList.txt file

                // input format - Official: nimal,nimal@gmail.com,ceo
                // Use a single input to get all the details of a recipient
                break;
            case 2:
                System.out.println("Enter mail contents in the following format\nEmail, Subject, Content");
                scanner.nextLine();
                String mailingDetails = scanner.nextLine();
                // System.out.println(mailingDetails);
                try {
                    String emailID = mailingDetails.split(",")[0].trim();
                    String subject = mailingDetails.split(",")[1].trim();
                    String content = mailingDetails.split(",")[2].trim();
                    JavaMail mail = new JavaMail(emailID); // create a mail_object
                    mail.sendMail(subject, content); // send mail
                } catch (UnknownHostException e) {
                    System.out.println("Check your network");
                } catch (ArrayIndexOutOfBoundsException e2) {
                    System.out.println("Invalid input type");
                }
                // input format - email, subject, content
                break;
            case 3:
                System.out.println("Enter the date in the following format - yyyy/MM/dd");
                scanner.nextLine();
                String birthDay = scanner.nextLine();
                printBirthdayRecipient(recipientObjList, birthDay);
                // input format - yyyy/MM/dd (ex: 2018/09/17)
                break;
            case 4:
                System.out.println("Enter the date in the following format - yyyy/MM/dd");
                scanner.nextLine();
                String mailDate = scanner.nextLine();
                ObjHandler.deserialize(mailDate);
                // input format - yyyy/MM/dd (ex: 2018/09/17)
                break;
            case 5:
                System.out.println("Number of Recipients on the list : " + recipientCount);
                break;

        }
        scanner.close();
    }

    public static ArrayList<Recipient> getRecipient() throws IOException {
        /**
         * This static method doesn't accept any parameter read the clientList text file
         * and create the respective object for the clients and add it to the ArrayList
         * for future use. This returns an ArrayList of Recipient objs
         */
        ArrayList<Recipient> recipientObjs = new ArrayList<Recipient>(); // create a new ArrayList to keep record of the
                                                                         // Recipient objs
        /**
         * first creating an instance of the file class to get the absolute path of
         * location. then passing that location into another file obj to read
         * clientList.txt file FilerReader obj is created by passing previously created
         * file obj
         */
        try {
            FileReader fr = new FileReader(new File((new File("").getAbsolutePath()) + "\\clientList.txt"));
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(""))
                    continue;
                String type = line.split(":")[0];
                String detail = line.split(":")[1];
                Recipient listRecipient = null;
                if (type.toUpperCase().equals("OFFICIAL")) {
                    listRecipient = new Official(type, detail);
                } else if (type.toUpperCase().equals("PERSONAL")) {
                    listRecipient = new Personal(type, detail);
                } else if (type.toUpperCase().equals("OFFICE_FRIEND")) {
                    listRecipient = new OfficialFriend(type, detail);
                }
                if (listRecipient != null)
                    recipientObjs.add(listRecipient);
            }

            br.close();
            return recipientObjs;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static void addRecipient(String type, String detail) {
        /**
         * This static method accepts two string parameters which are type of the
         * recipient and detail of that particular recipient. For every recipient the
         * detail should be in the correct form as mentioned below. Office_friend:
         * <name>,<Email Address>,<designation>,<Date of birth> Personal:
         * <name>,<nick-name>,<Email Address>,<Date of Birth> Official: <name>,<Email
         * address>,<designation> Doesn't return any attributes
         */
        String testType = type.toUpperCase();
        if (testType.equals("OFFICIAL")) {
            Official newRecipient = new Official(type, detail);
            newRecipient.addRecipient();
        } else if (testType.equals("PERSONAL")) {
            Personal newRecipient = new Personal(type, detail);
            newRecipient.addRecipient();
        } else if (testType.equals("OFFICE_FRIEND")) {
            OfficialFriend newRecipient = new OfficialFriend(type, detail);
            newRecipient.addRecipient();
        } else {
            System.out.println("Invalid Recipient type!");
        }
    }

    public static void printBirthdayRecipient(ArrayList<Recipient> recipientObjList, String birthDay) throws Exception {
        /*
         * This static method accepts an ArrayList of Recipient object in the clientList
         * and a particular date that has to be tested. print the recipents who have
         * birthday on that day, on the console Doesn't return any attributes
         */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate givenBirthday = LocalDate.parse(birthDay, formatter);
        boolean output = false; // to check whether someone has his birthday on that or not
        for (Recipient recipient : recipientObjList) {
            if (recipient instanceof BirthdaywishSendable) { // check whether the recipient is an instance of
                                                             // BirthdariwshSendable
                BirthdaywishSendable reci = (BirthdaywishSendable) recipient;
                if ((givenBirthday.getMonth() == reci.getBirthday().getMonth())
                        && (givenBirthday.getDayOfMonth() == reci.getBirthday().getDayOfMonth())) {
                    if (!output) {
                        System.out.println("People who have birthday on " + givenBirthday + " :");
                        output = true;
                    }
                    System.out.println(recipient.getName());
                }
            }
        }
        if (!output) {
            System.out.println("No one have birthday on " + givenBirthday);
        }
    }

    public static void sendBirthdayMail(ArrayList<Recipient> recipientObjList) throws Exception {
        /**
         * This static method accepts a parameter of ArrayList consist of Recipient
         * objects and check through the ArrayList of recipient and sends a JavaMail if
         * someone has birthday on the day the Email_client runs. Doesn't return any
         * attributes.
         */
        try {
            ArrayList<JavaMail> birthdayMails = ObjHandler.deserialize();
            LocalDate today = LocalDate.now(); // set today's date
            recipientLoop: for (Recipient recipient : recipientObjList) {
                if (recipient instanceof BirthdaywishSendable) {
                    for (JavaMail birthdayMail : birthdayMails) {
                        if (birthdayMail.getRecipient().equals(recipient.getMailId())) {
                            System.out.println("Birthday wish for " + recipient.getName() + " already sent");
                            System.out.println(
                                    "If you want sent again delete specific serialized mail object in the Folder");
                            break recipientLoop;
                        }
                    }
                    BirthdaywishSendable reci = (BirthdaywishSendable) recipient; // to send a birthday mail it has
                                                                                  // to
                                                                                  // implement Birthdaywishable
                    if ((today.getMonth() == reci.getBirthday().getMonth())
                            && (today.getDayOfMonth() == reci.getBirthday().getDayOfMonth())) {
                        JavaMail mail;
                        if (recipient instanceof Personal) { // check whether the recipient is Personal or
                                                             // OfficialFriend
                            Personal personal = (Personal) recipient;
                            mail = new JavaMail(personal.getMailId());
                            mail.sendMail("Birthday Wishes", "Hugs and love on your birthday");
                            System.out.println("Birthday wishes send for " + recipient.getName());
                        } else if (recipient instanceof OfficialFriend) {
                            OfficialFriend officialFriend = (OfficialFriend) recipient;
                            mail = new JavaMail(officialFriend.getMailId());
                            mail.sendMail("Birthday Wishes", "Wish you a Happy Birthday.");
                            System.out.println("Birthday wishes send for " + recipient.getName());
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            return;
        }
    }

}