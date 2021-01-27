package com.vanderkast.smsforwardapp.model;

public class Input {
    private final String phone;
    private final String email;
    private final String date;
    private final Mode mode;

    public Input(String phone, String email, String date, Mode mode) {
        this.phone = phone;
        this.email = email;
        this.date = date;
        this.mode = mode;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public Mode getMode() {
        return mode;
    }

    public enum Mode {
        PLAIN_TEXT,
        CSV_EMAIL,
        SERVER_1C
    }
}
