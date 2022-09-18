package com.messenger.ui.services;

import com.messenger.common.dto.UserDto;

import java.io.IOException;

public class UiActions {
    private HttpBackendClient httpBackendClient = new HttpBackendClient();

    public UserDto userLogInAction(String name) throws IOException, InterruptedException {
        UserDto user = httpBackendClient.userRequest(name);
        if (user == UserDto.EMPTY_USER_DTO) {  //it means no user with given name
            //ask to add a user
        } else { //user exists, updating status to active
            user.setActiveStatus(true);
            user = httpBackendClient.userUpdate(user);
        }
        return user;
    }

    public UserDto userLogOffAction(String name) throws IOException, InterruptedException {
        UserDto user = httpBackendClient.userRequest(name);
        if (user == UserDto.EMPTY_USER_DTO) {  //it means no user with given name
            //error message, its impossible if currently loggend user cannot be found
        } else { //user exists, updating status to inactive
            user.setActiveStatus(false);
            user = httpBackendClient.userUpdate(user);
        }
        return user;
    }
}
