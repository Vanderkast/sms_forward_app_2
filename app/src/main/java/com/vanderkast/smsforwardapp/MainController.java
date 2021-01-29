package com.vanderkast.smsforwardapp;

import com.vanderkast.smsforwardapp.model.Input;

public interface MainController {
    /**
     * @param input - user input values
     * @return resource code
     */
    int handle(Input input);
}
