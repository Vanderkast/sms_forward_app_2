package com.vanderkast.smsforwardapp.extension;

public interface ErrorKeeper {
    void save(Exception exception);
}
