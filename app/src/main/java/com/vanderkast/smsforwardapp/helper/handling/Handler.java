package com.vanderkast.smsforwardapp.helper.handling;

@FunctionalInterface
public interface Handler<I, R> {
    R handle(I input);
}
