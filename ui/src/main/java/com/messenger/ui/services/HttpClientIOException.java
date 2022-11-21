package com.messenger.ui.services;

import java.io.IOException;

public class HttpClientIOException extends IOException {
    public HttpClientIOException(String message) {
        super(message);
    }
}
