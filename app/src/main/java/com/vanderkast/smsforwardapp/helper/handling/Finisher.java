package com.vanderkast.smsforwardapp.helper.handling;

@FunctionalInterface
public interface Finisher<T> {
    Result proceed(T data);

    enum Result {
        DONE,
    }
}
