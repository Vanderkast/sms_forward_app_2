package com.vanderkast.smsforwardapp.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {
    public static final DateFormat DATE_FORMAT
            = new SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("ru_RU"));

    public static final DateFormat DATE_TIME_FORMAT
            = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.forLanguageTag("ru_RU"));
}
