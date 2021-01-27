package com.vanderkast.smsforwardapp.extension;

import java.util.Optional;

public interface Preference<T> {
    boolean contains();

    void remove();

    Optional<T> get();

    void save(T t);
}
