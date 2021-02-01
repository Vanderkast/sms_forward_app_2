package com.vanderkast.smsforwardapp.network;

import com.vanderkast.smsforwardapp.R;
import com.vanderkast.smsforwardapp.di.Settings;
import com.vanderkast.smsforwardapp.helper.handling.ErrorResult;
import com.vanderkast.smsforwardapp.helper.handling.Finisher;
import com.vanderkast.smsforwardapp.helper.handling.Result;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Response;

public class SendHistoryToServer implements Finisher<String> {
    private final Api api;

    @Inject
    public SendHistoryToServer(Api api) {
        this.api = api;
    }

    @Override
    public Result finish(String data) {
        try {
            Response<Void> response = api.send(Settings.AUTHORIZATION, data).execute();
            return response.code() == 200 ? Result.success() : Result.failure(R.string.sending_on_server_error);
        } catch (IOException e) {
            return new ErrorResult(R.string.server_error, e);
        }
    }
}
