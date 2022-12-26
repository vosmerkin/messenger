package com.messenger.backend.controllers;

import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.exception.UserNotFoundException;
import com.messenger.backend.services.UserService;
import com.messenger.common.dto.RoomDto;
import com.messenger.common.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getUser")    //request userinfo for logged in user
    public UserDto getUser(@RequestParam(value = "name") String name) {
        log.info("/getUser?name={}", name);
        UserEntity user = userService.getByUserName(name);
        UserDto responseUserDto;
        if (user == UserEntity.EMPTY_ENTITY) {
            throw new UserNotFoundException("User " + name + " not found");
        } else {
            responseUserDto = modelMapper.map(user, UserDto.class);
        }
        log.info("/getUser?name={} returning {}", name, responseUserDto);
        return responseUserDto;
    }
//    curl -XGET  \"http://localhost:8080/getUser?name=test_name\" "

    @GetMapping("/getUserStatus")    //request user Active status
    public Boolean getUserStatus(@RequestParam(value = "id") Integer id) {
        log.info("/getUserStatus?id={}", id);
        Boolean result = false;
        UserEntity user = userService.getByUserId(id);
        if (user == UserEntity.EMPTY_ENTITY) {
            throw new UserNotFoundException("User with id=" + id + " not found");
        } else {
            result = user.getActiveStatus();
        }
        return result;
    }

    @GetMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestParam(value = "name") String userName) {
        log.info("/createUser?name={}", userName);
        UserEntity user = userService.createUser(userName);
        UserDto responseUserDto = modelMapper.map(user, UserDto.class);
        return responseUserDto;
    }

    @PutMapping(value = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE) //update contact list
    public UserDto updateUser(@RequestBody UserDto userDto) {
        log.info("/updateUser_{}", userDto);
        UserEntity requestUser = modelMapper.map(userDto, UserEntity.class);
        UserEntity user = userService.updateUserStatus(requestUser);
        UserDto responseUserDto;
        if (user == UserEntity.EMPTY_ENTITY) {
            throw new UserNotFoundException("User " + userDto.getUserName() + " not found");
        } else {
            responseUserDto = modelMapper.map(user, UserDto.class);
        }
        return responseUserDto;
    }


}
