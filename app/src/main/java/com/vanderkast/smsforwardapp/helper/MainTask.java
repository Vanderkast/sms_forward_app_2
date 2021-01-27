package com.vanderkast.smsforwardapp.helper;

import android.os.AsyncTask;

import com.vanderkast.smsforwardapp.MainController;
import com.vanderkast.smsforwardapp.helper.handling.Finisher;
import com.vanderkast.smsforwardapp.model.Input;

public class MainTask extends AsyncTask<Input, Void, Finisher.Result> {
    private final MainController controller;

    public MainTask(MainController controller) {
        this.controller = controller;
    }

    @Override
    protected Finisher.Result doInBackground(Input... inputs) {
        return controller.handle(inputs[0]);
    }

    @Override
    protected void onPostExecute(Finisher.Result result) {
        System.out.println("KEKS: " + result.name());
    }
}
