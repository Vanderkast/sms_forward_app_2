package com.vanderkast.smsforwardapp.helper.handling;

@FunctionalInterface
public interface Finisher<T> {
    Result finish(T data);
}
