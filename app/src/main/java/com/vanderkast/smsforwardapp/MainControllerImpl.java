package com.vanderkast.smsforwardapp;

import androidx.core.util.Pair;

import com.vanderkast.smsforwardapp.di.MainComponent;
import com.vanderkast.smsforwardapp.email.EmailData;
import com.vanderkast.smsforwardapp.email.EmailDataBuilder;
import com.vanderkast.smsforwardapp.helper.handling.Finisher;
import com.vanderkast.smsforwardapp.helper.handling.HandlerChain;
import com.vanderkast.smsforwardapp.model.Input;

import java.util.Date;
import java.util.List;

public class MainControllerImpl implements MainController {
    private final MainComponent component;

    public MainControllerImpl(MainComponent component) {
        this.component = component;
    }

    @Override
    public Finisher.Result handle(Input input) {
        HandlerChain<List<Pair<Date, String>>> chain = HandlerChain.start(component.historyLoader(), input.getPhone())
                .next(component.historyReader())
                .next(component.smsDateFilter().apply(input.getDate()));

        if(input.getMode().equals(Input.Mode.SERVER_1C))
            return chain.next(component.historyToEmailText())
                    .finish(component.sendHistoryToServer());

        EmailDataBuilder emailData = new EmailDataBuilder();
        emailData.setSubject("SMS " + input.getPhone() + " " + input.getDate());
        emailData.setRecipientAddress(input.getEmail());
        if(input.getMode().equals(Input.Mode.PLAIN_TEXT)) {
            if(input.getPhone().equals("900"))
                emailData.setText(chain.next(component.simpleSberbankSmsHandler()).get());
            else
                emailData.setText(chain.next(component.historyToEmailText()).get());
        } else //check here if u add new Modes
            emailData.setAttachment(chain.next(component.saveCsv()).get());
        return component.sendEmail().finish(emailData.build());
    }
}
