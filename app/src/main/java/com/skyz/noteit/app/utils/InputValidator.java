package com.skyz.noteit.app.utils;

public class InputValidator {

    public boolean isInputNotEmpty(String input) {
        return input != null && input.length() > 0;
    }
}
