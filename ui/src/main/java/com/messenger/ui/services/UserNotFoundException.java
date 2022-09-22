package com.messenger.ui.services;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String str) {
        super(str);
    }
}
