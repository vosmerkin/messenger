package com.messenger.backend.services;

import com.messenger.backend.repository.MessageRepository;
import com.messenger.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class roomService {
    @Autowired
    private final RoomRepository roomRepository;

    public roomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
}
