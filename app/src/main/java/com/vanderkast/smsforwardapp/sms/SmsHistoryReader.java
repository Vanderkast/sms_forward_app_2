package com.vanderkast.smsforwardapp.sms;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.vanderkast.smsforwardapp.helper.handling.Handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class SmsHistoryReader implements Handler<Cursor, List<Pair<Date, String>>> {

    @Inject
    public SmsHistoryReader() {
    }

    @Override
    public List<Pair<Date, String>> handle(Cursor cursor) {
        List<Pair<Date, String>> history = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext())
                history.add(parse(cursor));
        }
        return history;
    }

    Pair<Date, String> parse(@NonNull Cursor cursor) {
        Date date = new Date(Long.parseLong(cursor.getString(1)));
        String text = cursor.getString(2);
        return new Pair<>(date, text);
    }
}
