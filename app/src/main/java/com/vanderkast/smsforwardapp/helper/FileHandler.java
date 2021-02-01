package com.vanderkast.smsforwardapp.helper;

import com.vanderkast.smsforwardapp.helper.handling.Handler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.inject.Inject;

public class FileHandler implements Handler<String, Optional<String>> {
    @Inject
    public FileHandler() {
    }

    @Override
    public Optional<String> handle(String fileName) {
        try {
            InputStream inputStream = new FileInputStream(fileName);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            return Optional.of(new String(data));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
