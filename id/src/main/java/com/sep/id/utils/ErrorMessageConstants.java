package com.sep.id.utils;

public class ErrorMessageConstants {
    public static final String WRONG_EMAIL = "Email is not in the right format.";
    public static final String EMPTY_EMAIL = "Email cannot be empty.";
    public static final String TOO_LONG_EMAIL = "Email length is too long. Email cannot contain more than 60 characters.";
    public static final String WRONG_PASSWORD =
            "Password must contain at least 8 characters. " +
                    "At least one number and one special character.";
    public static final String PASSWORD_NOT_LONG_ENOUGH = "Password must be at least 12 characters long.";
    public static final String WRONG_VERIFY_HASH = "Verify hash must be added.";
    public static final String NULL_VALUE = "Attribute must not be empty.";
    public static final String EMPTY_STRING = "String attribute must not be empty";

    public static final String NOT_FOUND_USER = "User is not found.";

}
