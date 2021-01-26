package com.vanderkast.smsforwardapp.sms;

import com.vanderkast.smsforward.model.Input;

public interface SmsHandlersChain<T> {
    static SmsHandlersChain<?> getInstance(Input input) {
        return ;
    }

    T run(Input input);

    default String standardSubject(Input input) {
        return "SMS " + input.getNumber() + " " + input.getDate();
    }
}
