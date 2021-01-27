package com.vanderkast.smsforwardapp.extension;


import android.widget.Button;

public interface KeepValueButton<T> {
    void bind(Button button);

    void onValueChanged();

    void onClick();
}
