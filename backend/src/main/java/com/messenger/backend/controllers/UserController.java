package com.messenger.backend.controllers;

import com.messenger.backend.entity.UserDto;
import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.services.UserService;
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

    @GetMapping("/getUser")    //request userinfo for logged in user
    public UserEntity getUser(@RequestParam(value = "name") String name) {
        log.info("/getUser?name={}", name);
        return userService.getByUserName(name);
    }

    @GetMapping("/getUserStatus")    //request user Active status
    public Boolean getUserStatus(@RequestParam(value = "id") Integer id) {
        log.info("/getUserStatus?id={}", id);
        return userService.getUserStatus(id);
    }

    @PutMapping(value = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE) //update contact list


    public UserEntity updateUser(@Validated(UserDto.UpdateContactList.class) @RequestBody UserDto userDto) {
        log.info("/updateUser_{}", userDto);
        return userService.UpdateContactList(userDto.toEntity());
    }


}
