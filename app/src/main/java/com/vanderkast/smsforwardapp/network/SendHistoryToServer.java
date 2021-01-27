package com.vanderkast.smsforwardapp.network;

import com.vanderkast.smsforwardapp.di.Settings;
import com.vanderkast.smsforwardapp.helper.handling.Finisher;

import java.io.IOException;

import javax.inject.Inject;

public class SendHistoryToServer implements Finisher<String> {
    private final Api api;

    @Inject
    public SendHistoryToServer(Api api) {
        this.api = api;
    }

    @Override
    public Result finish(String data) {
        try {
            api.send(Settings.AUTHORIZATION, data).execute();
            return Result.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }
}
