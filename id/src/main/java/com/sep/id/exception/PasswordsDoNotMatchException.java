package com.sep.id.exception;


import static com.sep.id.utils.Constants.PASSWORDS_DO_NOT_MATCH_MESSAGE;

public class PasswordsDoNotMatchException extends AppException {

    public PasswordsDoNotMatchException() {
        super(PASSWORDS_DO_NOT_MATCH_MESSAGE);
    }

    public PasswordsDoNotMatchException(String message) {
        super(message);
    }
}
