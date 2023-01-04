package com.messenger.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoomCreateFailed extends RuntimeException {
    public RoomCreateFailed(String message) {
        super(message);
    }
}
