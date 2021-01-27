package com.vanderkast.smsforwardapp.email;

public interface EmailSender {
    void send(EmailData data) throws Exception;
}
