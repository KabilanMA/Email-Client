import java.io.*;

public class Official extends Recipient {
    private String detail;
    private final String type;

    Official(String type, String detail) {
        super(type, detail);
        this.type = type;
        this.detail = detail;
    }

    @Override
    public void addRecipient() {
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
            System.out.println("An Official Recipient added successfully!");
        } catch (IOException e) {
            System.out.println("Couldn't add a new recipient because of " + e.toString());
        }
    }

    @Override
    public String getMailId() {
        return detail.split(",")[1].trim();
    }
}
