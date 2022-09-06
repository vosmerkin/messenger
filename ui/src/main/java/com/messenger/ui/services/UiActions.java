package com.messenger.ui.services;

import com.messenger.common.dto.UserDto;

public class UiActions {
    private HttpBackendClient httpBackendClient = new HttpBackendClient();

    public UserDto userLogInAction(String name) {
        UserDto user = httpBackendClient.userRequest(name);
        if (user==UserDto.EMPTY_USER_DTO){  //no user with given name
            //ask to add a user
        } else{ //user exists, updating status
            user = httpBackendClient.userUpdate()

        }

    }
}
