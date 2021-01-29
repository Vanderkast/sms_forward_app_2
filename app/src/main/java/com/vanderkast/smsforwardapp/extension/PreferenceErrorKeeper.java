package com.vanderkast.smsforwardapp.extension;

public class PreferenceErrorKeeper implements ErrorKeeper {
    private final StringPreference preference;

    public PreferenceErrorKeeper(StringPreference preference) {
        this.preference = preference;
    }

    @Override
    public void save(Exception exception) {
        preference.save(exception.getMessage() + '\n' + exception.getCause());
    }
}
