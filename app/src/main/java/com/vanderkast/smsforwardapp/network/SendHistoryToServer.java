package com.vanderkast.smsforwardapp.network;

import com.vanderkast.smsforwardapp.R;
import com.vanderkast.smsforwardapp.di.Settings;
import com.vanderkast.smsforwardapp.helper.handling.ErrorResult;
import com.vanderkast.smsforwardapp.helper.handling.Finisher;
import com.vanderkast.smsforwardapp.helper.handling.Result;

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
            return Result.success();
        } catch (IOException e) {
            return new ErrorResult(R.string.server_error, e);
        }
    }
}
