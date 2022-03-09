import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Personal extends Recipient implements BirthdaywishSendable {
    /**
     * This class is a subclass of abstract class recipient and also implements the
     * BirthdaywishSendable interface therefore it has to implement the the abstract
     * methods addRecipient and getMailID from abstarct class Recipient and also
     * implements the abstract method getBirthday of interface BithdaywishSendable
     */
    private String detail;
    private final String type;
    private LocalDate birthDay;

    public Personal(String type, String detail) {
        super(type, detail);
        this.type = type;
        this.detail = detail;
    }

    // overridden method of the abstarct class Recipient
    @Override
    public void addRecipient() {
        /**
         * This method doesn't accepts any attributes it add the recipient details into
         * the clientList.txt file
         */
        try {
            String path = new File("").getAbsolutePath();
            File clientDetails = new File(path + "\\clientList.txt");
            FileWriter writer;
            if (clientDetails.createNewFile()) {
                writer = new FileWriter(path + "\\clientList.txt");
            } else {
                writer = new FileWriter(path + "\\clientList.txt", true);
            }
            detail = type + ": " + detail + "\n";
            writer.append(detail);
            writer.close();
            System.out.println("A Personal Recipient added successfully!");
        } catch (IOException e) {
            System.out.println("Couldn't add a new recipient because : " + e.toString());
        }
    }

    @Override
    public LocalDate getBirthday() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        String birthday = detail.split(",")[3].trim();
        birthDay = LocalDate.parse(birthday, formatter);
        return birthDay;
    }

    @Override
    public String getMailId() {
        return detail.split(",")[2].trim();
    }
}
