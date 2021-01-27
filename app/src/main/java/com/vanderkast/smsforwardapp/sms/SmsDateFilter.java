package com.vanderkast.smsforwardapp.sms;

import androidx.core.util.Pair;

import com.vanderkast.smsforwardapp.helper.DateUtil;
import com.vanderkast.smsforwardapp.helper.handling.Filter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class SmsDateFilter implements Filter<List<Pair<Date, String>>> {

    private final String date;

    public SmsDateFilter(String date) {
        this.date = date;
    }

    @Override
    public List<Pair<Date, String>> handle(List<Pair<Date, String>> history) {
        return history.stream()
                .filter(pair -> pair.first != null && DateUtil.DATE_FORMAT.format(pair.first).equals(date))
                .collect(Collectors.toList());
    }
}
