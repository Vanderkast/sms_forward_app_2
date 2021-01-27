package com.vanderkast.smsforwardapp.helper.handling;

@FunctionalInterface
public interface Filter<T> extends Handler<T, T> {
}
