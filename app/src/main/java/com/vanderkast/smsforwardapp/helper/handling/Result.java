package com.vanderkast.smsforwardapp.helper.handling;

import com.vanderkast.smsforwardapp.R;

public class Result {
    public static final int NO_MESSAGE = -1;

    private final Type type;
    private final int messageCode;

    public Result(Type type, int message) {
        this.type = type;
        this.messageCode = message;
    }

    public static Result success(int message) {
        return new Result(Type.SUCCESS, message);
    }

    public static Result success() {
        return new Result(Type.SUCCESS, R.string.success);
    }

    public static Result failure(int message) {
        return new Result(Type.FAILURE, message);
    }

    public Type getType() {
        return type;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public enum Type {
        SUCCESS,
        FAILURE;
    }
}
