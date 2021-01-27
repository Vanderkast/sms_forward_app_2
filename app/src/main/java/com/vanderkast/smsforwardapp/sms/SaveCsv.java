package com.vanderkast.smsforwardapp.sms;

import android.content.Context;

import androidx.core.util.Pair;

import com.opencsv.CSVWriter;
import com.vanderkast.smsforwardapp.helper.handling.Handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.vanderkast.smsforwardapp.helper.DateUtil.DATE_FORMAT;

public final class SaveCsv implements Handler<List<Pair<Date, String>>, String> {
    public final static String CSV_FILE = "history.csv";

    private final Context context;

    @Inject
    public SaveCsv(Context context) {
        this.context = context;
    }

    @Override
    public String handle(List<Pair<Date, String>> history) {
        CSVWriter writer;
        try {
            String path = getPath(context);
            writer = new CSVWriter(new FileWriter(path, false));
            history.forEach(pair -> writer.writeNext(new String[]{DATE_FORMAT.format(pair.first), pair.second}));
            writer.close();
            return path;
        } catch (IOException e) {
            throw new SaveCsvException(e);
        }
    }

    private String getPath(Context context) {
        return context.getApplicationInfo().dataDir + File.separatorChar + CSV_FILE;
    }

    static class SaveCsvException extends RuntimeException {
        public SaveCsvException(Throwable cause) {
            super(cause);
        }
    }
}
