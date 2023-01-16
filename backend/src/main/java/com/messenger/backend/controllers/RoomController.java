package com.messenger.backend.controllers;

import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.exception.RoomCreateFailed;
import com.messenger.backend.exception.RoomNotFoundException;
import com.messenger.backend.exception.UserCreateFailed;
import com.messenger.backend.exception.UserNotFoundException;
import com.messenger.backend.services.RoomService;
import com.messenger.common.dto.RoomDto;
import com.messenger.common.dto.RoomDto;
import com.messenger.common.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {
    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;
    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/getRoom")    //request roominfo for current room
    public RoomDto getRoom(@RequestParam(value = "name") String name) {
        log.info("/getRoom?name={}", name);
        RoomEntity room = roomService.getByRoomName(name);
        RoomDto responseRoomDto;
        if (room == RoomEntity.EMPTY_ENTITY) {
            log.debug("Room at /getRoom?name={} not found, throwing exception", name);
            throw new RoomNotFoundException("Room " + name + " not found");
        } else {
            responseRoomDto = modelMapper.map(room, RoomDto.class);
        }
        log.info("/getRoom?name={} returning {}", name, responseRoomDto);
        return responseRoomDto;
    }
//    curl -XGET  \"http://localhost:8080/getRoom?name=test_name\" "


    @GetMapping(value = "/createRoom")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomDto createRoom(@RequestParam(value = "name") String roomName) {
        log.info("/createRoom?name={}", roomName);
        if (roomName.isEmpty())
            throw new RoomCreateFailed("Room name cannot be empty");
        RoomEntity room = roomService.createRoom(roomName);
        RoomDto responseRoomDto;
        if (room == RoomEntity.EMPTY_ENTITY) {
            throw new RoomCreateFailed("Room " + roomName + " already exists");
        } else {
            responseRoomDto = modelMapper.map(room, RoomDto.class);
        }
        return responseRoomDto;
    }

    @PutMapping(value = "/updateRoomUsers", consumes = MediaType.APPLICATION_JSON_VALUE) //update room rooms list
    public RoomDto updateRoom(@RequestBody RoomDto roomDto) {
        log.info("/updateRoom_{}", roomDto);
        RoomEntity requestRoom = modelMapper.map(roomDto, RoomEntity.class);
        RoomEntity room = roomService.updateRoom(requestRoom);
        RoomDto responseRoomDto;
        if (room == RoomEntity.EMPTY_ENTITY) {
            throw new RoomNotFoundException("Room " + roomDto.getRoomName() + " not found");
        } else {
            responseRoomDto = modelMapper.map(room, RoomDto.class);
        }
        return responseRoomDto;
    }


}
