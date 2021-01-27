package com.vanderkast.smsforwardapp.email;

import com.vanderkast.smsforwardapp.helper.handling.Finisher;

import javax.inject.Inject;

public class SendEmailFinisher implements Finisher<EmailData> {
    private final EmailSender sender;

    @Inject
    public SendEmailFinisher(EmailSender sender) {
        this.sender = sender;
    }

    @Override
    public Result finish(EmailData data) {
        try {
            sender.send(data);
            return Result.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }
}
