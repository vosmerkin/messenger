package com.messenger.ui.services;

import com.messenger.common.dto.UserDto;

import java.io.IOException;

public class UiAction {
    private HttpBackendClient httpBackendClient = new HttpBackendClient();

    public UserDto userLogInAction(String name) throws IOException, InterruptedException, UserNotFoundException {
        UserDto user = httpBackendClient.userRequest(name);
        user.setActiveStatus(true);
        user = httpBackendClient.userUpdate(user);
        return user;
    }

    public UserDto userLogOffAction(UserDto user) throws IOException, InterruptedException, UserNotFoundException {
        user.setActiveStatus(false);
        user = httpBackendClient.userUpdate(user);
        return user;
    }
}
