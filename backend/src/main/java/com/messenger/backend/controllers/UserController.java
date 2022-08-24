package com.messenger.backend.controllers;

import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.services.UserService;
import com.messenger.common.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    private ModelMapper modelMapper;

    @GetMapping("/getUser")    //request userinfo for logged in user
    public UserDto getUser(@RequestParam(value = "name") String name) {
        log.info("/getUser?name={}", name);
        UserEntity user = userService.getByUserName(name);
        UserDto responseUserDto = modelMapper.map(user, UserDto.class);
        return responseUserDto;
    }

    @GetMapping("/getUserStatus")    //request user Active status
    public Boolean getUserStatus(@RequestParam(value = "id") Integer id) {
        log.info("/getUserStatus?id={}", id);
        return userService.getUserStatus(id);
    }

    @PutMapping(value = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE) //update contact list


    public UserDto updateUser(@Validated(UserDto.UpdateContactList.class) @RequestBody UserDto userDto) {
        log.info("/updateUser_{}", userDto);
        UserEntity requestUser = modelMapper.map(userDto, UserEntity.class);
        UserEntity user = userService.UpdateContactList(requestUser);
        UserDto responseUserDto = modelMapper.map(user, UserDto.class);
        return responseUserDto;
    }


}
