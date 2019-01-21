package com.skyz.noteit.ui.login;

import com.skyz.noteit.app.utils.InputValidator;

public class UserValidator extends InputValidator {

    public boolean isUserValid(String login, String password) {
        return isLoginValid(login) && isPasswordValid(password);
    }

    public boolean isLoginValid(String login) {
        return super.isInputNotEmpty(login);
    }

    public boolean isPasswordValid(String password) {
        return super.isInputNotEmpty(password);
    }

    public boolean isTokenValid(String token) {
        return super.isInputNotEmpty(token);
    }
}
