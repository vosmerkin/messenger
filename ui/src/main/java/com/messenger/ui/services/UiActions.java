package com.messenger.ui.services;

import com.messenger.common.dto.UserDto;

public class UiActions {
    private HttpBackendClient httpBackendClient = new HttpBackendClient();

    public UserDto userLogInAction(String name) {
        UserDto user = httpBackendClient.userRequest(name);
        if (user == UserDto.EMPTY_USER_DTO) {  //it means no user with given name
            //ask to add a user
        } else { //user exists, updating status to active
            user.setActiveStatus(true);
            httpBackendClient.userUpdate(user);
        }
        return user;
    }

    public UserDto userLogOffAction(String name) {
        UserDto user = httpBackendClient.userRequest(name);
        if (user == UserDto.EMPTY_USER_DTO) {  //it means no user with given name
            //error message, its impossible if currently loggend user cannot be found
        } else { //user exists, updating status to inactive
            user.setActiveStatus(false);
            httpBackendClient.userUpdate(user);
        }
        return user;
    }
}
