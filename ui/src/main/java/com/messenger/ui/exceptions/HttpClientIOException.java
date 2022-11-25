package com.messenger.ui.exceptions;

import java.io.IOException;

public class HttpClientIOException extends IOException {
    public HttpClientIOException(String message) {
        super(message);
    }
}
