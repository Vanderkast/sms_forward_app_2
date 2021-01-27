package com.vanderkast.smsforwardapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.vanderkast.smsforwardapp.di.DaggerMainComponent;
import com.vanderkast.smsforwardapp.di.DependencyProvider;
import com.vanderkast.smsforwardapp.di.MainComponent;
import com.vanderkast.smsforwardapp.extension.DatePickerFragment;
import com.vanderkast.smsforwardapp.extension.KeepValueButton;
import com.vanderkast.smsforwardapp.extension.KeepValueButtonImpl;
import com.vanderkast.smsforwardapp.extension.Preference;
import com.vanderkast.smsforwardapp.extension.StringPreference;
import com.vanderkast.smsforwardapp.extension.ValueTextWatcher;
import com.vanderkast.smsforwardapp.helper.MainTask;
import com.vanderkast.smsforwardapp.model.Input;

import java.util.Calendar;
import java.util.Date;

import static com.vanderkast.smsforwardapp.helper.DateUtil.DATE_FORMAT;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final int PERMISSION_REQUEST_CODE = 1907;
    public static final String PREFERENCES_NAME = "sms_forward_app_prefs";

    private final MainComponent component = DaggerMainComponent.builder()
            .dependencyProvider(new DependencyProvider(this))
            .build();
    private final MainController mainController = new MainControllerImpl(component);

    private SharedPreferences preferences;

    private EditText phone;
    private EditText email;
    private EditText date;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        setUpPhoneRow();
        setUpEmailRow();
        setUpDatePicker();
        setUpSendButton();
    }

    //todo: remove code duplication in #setUpPhoneRow and #setUpEmailRow methods
    private void setUpPhoneRow() {
        phone = findViewById(R.id.phone_input);
        Button button = findViewById(R.id.save_phone_button);
        final Preference<String> preference = new StringPreference(preferences, "phone");
        KeepValueButton<String> keeper = new KeepValueButtonImpl<>(
                preference,
                phone.getText()::toString);
        keeper.bind(button);
        preference.get().ifPresent(phone::setText);
        phone.addTextChangedListener(new ValueTextWatcher(keeper));
    }

    private void setUpEmailRow() {
        email = findViewById(R.id.email_input);
        final Button button = findViewById(R.id.save_email_button);
        final Preference<String> preference = new StringPreference(preferences, "email");
        final KeepValueButton<String> keeper = new KeepValueButtonImpl<>(
                preference,
                email.getText()::toString);
        keeper.bind(button);
        preference.get().ifPresent(email::setText);
        email.addTextChangedListener(new ValueTextWatcher(keeper));
    }

    @SuppressLint("SimpleDateFormat")
    private void setUpDatePicker() {
        date = (EditText) findViewById(R.id.date_input);
        date.setText(DATE_FORMAT.format(new Date()));
        (findViewById(R.id.date_button)).setOnClickListener(view -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date_picker");
        });
    }

    private void setUpSendButton() {
        send = findViewById(R.id.send_button);
        send.setOnClickListener((v) -> askPermissionAndShowModeMenu());
    }

    private void askPermissionAndShowModeMenu() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                == PackageManager.PERMISSION_GRANTED)
            showSendModeListMenu();
        else
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                Snackbar.make(findViewById(R.id.main_coordinator_layout), getString(R.string.no_permission_granted), Snackbar.LENGTH_LONG).show();
            else
                showSendModeListMenu();
        }
    }

    private void showSendModeListMenu() {
        PopupMenu popup = new PopupMenu(getApplicationContext(), send);
        popup.getMenuInflater().inflate(R.menu.send_modes_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> onSendModeSelected(item.getItemId()));
        popup.show();
    }

    private boolean onSendModeSelected(int mode_id) {
        if (mode_id == R.id.mode_plain_email)
            handle(Input.Mode.PLAIN_TEXT);
        if (mode_id == R.id.mode_csv_email)
            handle(Input.Mode.CSV_EMAIL);
        if (mode_id == R.id.mode_server_1c)
            handle(Input.Mode.SERVER_1C);
        return true;
    }

    private void handle(Input.Mode mode) {
        new MainTask(mainController).execute(new Input(phone.getText().toString(),
                email.getText().toString(),
                date.getText().toString(),
                mode));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DATE_FORMAT.format(c.getTime());
        currentDateString = currentDateString.replaceAll("/", ".");

        EditText textView = findViewById(R.id.date_input);
        textView.setText(currentDateString);
    }
}