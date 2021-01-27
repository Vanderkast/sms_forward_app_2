package com.vanderkast.smsforwardapp.sms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.vanderkast.smsforwardapp.helper.handling.Handler;

import javax.inject.Inject;

public final class HistoryLoader implements Handler<String, Cursor> {
    private final Context context;

    @Inject
    public HistoryLoader(Context context) {
        this.context = context;
    }

    public Cursor handle(String number) {
        Uri uriSms = Uri.parse("content://sms/inbox");
        return context.getContentResolver()
                .query(
                        uriSms,
                        new String[]{"address", "date", "body"},
                        "address=?",
                        new String[]{number},
                        null);
    }
}
