package com.vanderkast.smsforwardapp.extension;

import android.annotation.SuppressLint;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.vanderkast.smsforwardapp.R;

import java.util.function.Supplier;

/**
 * <p>Keeps value in <code>Preference<T></code> object.</p>
 * <p>Sets view with lock closed/open icon according to containing value in preference.</p>
 * <p>If user changes value and preference contains an one already, lock status will be reset via setting null to preference</p>
 *
 * @param <T> type of keeping value
 */
public class KeepValueButtonImpl<T> implements KeepValueButton<T> {
    private final Preference<T> preference;
    private final Supplier<T> value;

    private Button button;
    private boolean saved;

    /**
     * @param preference ~ value storing handler
     * @param value      supplier for current value
     */
    public KeepValueButtonImpl(Preference<T> preference, Supplier<T> value) {
        this.saved = preference.contains();
        this.preference = preference;
        this.value = value;
    }

    @Override
    public void bind(Button button) {
        this.button = button;
        button.setOnClickListener((v) -> onClick());
        updateView();
    }

    @Override
    public void onClick() {
        if (saved)
            preference.remove();
        else
            preference.save(value.get());
        saved = !saved;
        updateView();
    }

    void updateView() {
        if (saved) {
            setIcon(R.drawable.lock_closed);
        } else {
            setIcon(R.drawable.lock_open);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void setIcon(int res) {
        ((MaterialButton) button).setIcon(button.getContext().getDrawable(res));
    }

    @Override
    public void onValueChanged() {
        if (saved) {
            preference.remove();
            saved = !saved;
            updateView();
        }
    }
}
