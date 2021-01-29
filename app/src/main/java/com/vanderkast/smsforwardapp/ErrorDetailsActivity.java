package com.vanderkast.smsforwardapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vanderkast.smsforwardapp.extension.Preference;
import com.vanderkast.smsforwardapp.extension.StringPreference;

public class ErrorDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_details);

        Preference<String> preference = new StringPreference(getSharedPreferences(MainActivity.PREFERENCES_NAME, MODE_PRIVATE), "error");
        preference.get().ifPresent(((TextView) findViewById(R.id.error_details_text_view))::setText);
    }
}
