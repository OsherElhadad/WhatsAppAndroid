package com.example.whatsappandroid;

public class Contact {
    private String userName;
    private int pictureId;
    private String lastMassage;
    private String lastMassageSendingTime;

    public Contact(String userName, int pictureId, String lastMassage, String lastMassageSendingTime) {
        this.userName = userName;
        this.pictureId = pictureId;
        this.lastMassage = lastMassage;
        this.lastMassageSendingTime = lastMassageSendingTime;
    }

    public Contact(String userName ,String lastMassage, String lastMassageSendingTime) {
        this.userName = userName;

        this.lastMassage = lastMassage;
        this.lastMassageSendingTime = lastMassageSendingTime;
    }

    public int getPictureId() {
        return pictureId;
    }

    public String getLastMassage() {
        return lastMassage;
    }

    public String getLastMassageSendingTime() {
        return lastMassageSendingTime;
    }

    public String getUserName() {
        return userName;
    }
}
