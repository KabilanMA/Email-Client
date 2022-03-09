/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myPack;


public abstract class Recipient {
    private final String detail;
    private final String type;
    private String name;
    private static int count = 0;

    Recipient(String type, String detail) {
        this.detail = detail;
        this.type = type;
        count++;
    }

    abstract public String getMailId();

    abstract public void addRecipient();

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public String getDetail() {
        return detail;
    }

    public String getName() {
        name = detail.split(",")[0].trim();
        return name;
    }
}
