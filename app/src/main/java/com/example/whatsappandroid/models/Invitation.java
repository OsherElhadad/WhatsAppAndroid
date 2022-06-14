package com.example.whatsappandroid.models;

public class Invitation {
    private String from;
    private String to;
    private String server;

    public Invitation(String from, String to, String server) {
        this.from = from;
        this.to = to;
        this.server = server;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
