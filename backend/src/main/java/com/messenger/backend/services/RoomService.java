package com.messenger.backend.services;

import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class RoomService {
    @Autowired
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomEntity createRoom(String roomName) {
        RoomEntity newRoom = new RoomEntity(null, roomName, Collections.emptySet());
        return roomRepository.save(newRoom);
    }
    
    public RoomEntity getByRoomName(String roomName) {
        return roomRepository.findByRoomName(roomName);
    }


    

}
