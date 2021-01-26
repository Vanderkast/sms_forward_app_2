package com.vanderkast.smsforwardapp.sms.handlers;

import android.annotation.SuppressLint;

import androidx.core.util.Pair;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class SmsDateFilterImpl implements SmsDateFilter {
    @Inject
    public SmsDateFilterImpl() {
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public List<Pair<Date, String>> doFilter(List<Pair<Date, String>> history, String date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return history.stream()
                .filter(pair -> pair.first != null && dateFormat.format(pair.first).equals(date))
                .collect(Collectors.toList());
    }
}
