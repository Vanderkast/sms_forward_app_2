package com.vanderkast.smsforwardapp.di;

import com.vanderkast.smsforwardapp.email.EmailSender;
import com.vanderkast.smsforwardapp.email.SendEmailFinisher;
import com.vanderkast.smsforwardapp.extension.ErrorKeeper;
import com.vanderkast.smsforwardapp.helper.handling.ErrorResult;
import com.vanderkast.smsforwardapp.network.Api;
import com.vanderkast.smsforwardapp.network.SendHistoryToServer;
import com.vanderkast.smsforwardapp.sms.HistoryLoader;
import com.vanderkast.smsforwardapp.sms.HistoryToEmailText;
import com.vanderkast.smsforwardapp.sms.SaveCsv;
import com.vanderkast.smsforwardapp.sms.SimpleSberbankSmsHandler;
import com.vanderkast.smsforwardapp.sms.SmsDateFilter;
import com.vanderkast.smsforwardapp.sms.SmsHistoryReader;

import java.util.function.Function;

import dagger.Component;

@Component(modules = DependencyProvider.class)
public interface MainComponent { //todo: divide on sub-components
    HistoryLoader historyLoader();

    SmsHistoryReader historyReader();

    HistoryToEmailText historyToEmailText();

    SaveCsv saveCsv();

    SimpleSberbankSmsHandler simpleSberbankSmsHandler();

    Function<String, SmsDateFilter> smsDateFilter();

    Api api();

    SendHistoryToServer sendHistoryToServer();

    EmailSender emailSender();

    SendEmailFinisher sendEmail();

    ErrorKeeper errorKeeper();
}
