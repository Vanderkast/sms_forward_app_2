package com.vanderkast.smsforwardapp.extension;

import android.content.SharedPreferences;

import java.util.Optional;

public class StringPreference implements Preference<String> {
    private final SharedPreferences preferences;
    private final String key;

    public StringPreference(SharedPreferences preferences, String key) {
        this.preferences = preferences;
        this.key = key;
    }

    @Override
    public boolean contains() {
        return preferences.contains(key);
    }

    @Override
    public void remove() {
        preferences.edit().remove(key).apply();
    }

    @Override
    public Optional<String> get() {
        return Optional.ofNullable(preferences.getString(key, null));
    }

    @Override
    public void save(String value) {
        preferences.edit().putString(key, value).apply();
    }
}
