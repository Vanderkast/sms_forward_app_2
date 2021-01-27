package com.vanderkast.smsforwardapp.extension;

import android.text.Editable;
import android.text.TextWatcher;

public class ValueTextWatcher implements TextWatcher {
    private final KeepValueButton<?> valueButton;

    public ValueTextWatcher(KeepValueButton<?> valueButton) {
        this.valueButton = valueButton;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // ignore
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // ignore
    }

    @Override
    public void afterTextChanged(Editable s) {
        valueButton.onValueChanged();
    }
}
