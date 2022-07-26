package com.messenger.backend.services;

import com.messenger.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomService {
    @Autowired
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
}
