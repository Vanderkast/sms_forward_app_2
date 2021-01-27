package com.vanderkast.smsforwardapp;

import com.vanderkast.smsforwardapp.helper.handling.Finisher;
import com.vanderkast.smsforwardapp.model.Input;

public interface MainController {
    Finisher.Result handle(Input input);
}
