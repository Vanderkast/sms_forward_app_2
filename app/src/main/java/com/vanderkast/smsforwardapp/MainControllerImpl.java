package com.vanderkast.smsforwardapp;

import androidx.core.util.Pair;

import com.google.gson.internal.$Gson$Types;
import com.vanderkast.smsforwardapp.di.MainComponent;
import com.vanderkast.smsforwardapp.email.EmailDataBuilder;
import com.vanderkast.smsforwardapp.helper.handling.ErrorResult;
import com.vanderkast.smsforwardapp.helper.handling.HandlerChain;
import com.vanderkast.smsforwardapp.helper.handling.Result;
import com.vanderkast.smsforwardapp.model.Input;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MainControllerImpl implements MainController {
    private final MainComponent component;

    public MainControllerImpl(MainComponent component) {
        this.component = component;
    }

    @Override
    public int handle(Input input) {
        return handle(proceed(input));
    }

    private Result proceed(Input input) {
        HandlerChain<List<Pair<Date, String>>> chain = HandlerChain.start(component.historyLoader(), input.getPhone())
                .next(component.historyReader())
                .next(component.smsDateFilter().apply(input.getDate()));

        if (input.getMode().equals(Input.Mode.SERVER_1C)){
            Optional<String> data = chain.next(component.saveCsv())
                    .next(component.fileHandler())
                    .get();
            if(data.isPresent())
                return component.sendHistoryToServer().finish(data.get());
            else return Result.failure(R.string.no_file_found_to_send);
        }

        EmailDataBuilder emailData = new EmailDataBuilder();
        emailData.setSubject("SMS " + input.getPhone() + " " + input.getDate());
        emailData.setRecipientAddress(input.getEmail());
        if (input.getMode().equals(Input.Mode.PLAIN_TEXT)) {
            if (input.getPhone().equals("900"))
                emailData.setText(chain.next(component.simpleSberbankSmsHandler()).get());
            else
                emailData.setText(chain.next(component.historyToEmailText()).get());
        } else //check here if u add new SendModes
            emailData.setAttachment(chain.next(component.saveCsv()).get());
        return component.sendEmail().finish(emailData.build());
    }

    private int handle(Result result) {
        if(result instanceof ErrorResult)
            component.errorKeeper().save(((ErrorResult) result).getException());
        return result.getMessageCode();
    }
}
