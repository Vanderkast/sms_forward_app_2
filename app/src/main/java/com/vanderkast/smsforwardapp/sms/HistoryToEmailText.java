package com.vanderkast.smsforwardapp.sms;

import android.annotation.SuppressLint;

import androidx.core.util.Pair;

import com.vanderkast.smsforwardapp.helper.handling.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public final class HistoryToEmailText implements Handler<List<Pair<Date, String>>, String> {

    @Inject
    public HistoryToEmailText() {
    }

    @SuppressLint("SimpleDateFormat")
    public String handle(List<Pair<Date, String>> history) {
        StringBuilder builder = new StringBuilder();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        history.forEach(pair ->
                builder.append(timeFormat.format(pair.first))
                        .append(": ")
                        .append(pair.second)
        );
        return builder.toString();
    }
}
