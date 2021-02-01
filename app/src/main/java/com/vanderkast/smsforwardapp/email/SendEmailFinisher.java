package com.vanderkast.smsforwardapp.email;

import com.vanderkast.smsforwardapp.R;
import com.vanderkast.smsforwardapp.helper.handling.ErrorResult;
import com.vanderkast.smsforwardapp.helper.handling.Finisher;
import com.vanderkast.smsforwardapp.helper.handling.Result;

import javax.inject.Inject;
import javax.mail.MessagingException;

import retrofit2.Response;

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
            return Result.success();
        } catch (MessagingException e) {
            return new ErrorResult(R.string.email_error, e);
        } catch (Exception e) {
            return new ErrorResult(R.string.unknown_email_error, e);
        }
    }
}
