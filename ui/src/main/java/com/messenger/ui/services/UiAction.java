package com.messenger.ui.services;

import com.messenger.common.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;

public class UiAction {
    private static final Logger log = LoggerFactory.getLogger(HttpBackendClient.class);
    private HttpBackendClient httpBackendClient = new HttpBackendClient();

    public UserDto userLogInAction(String userName) {
        UserDto user = null;
        try {
            user = httpBackendClient.userRequest(userName);
            user.setActiveStatus(true);
            user = httpBackendClient.userUpdate(user);
        } catch (IOException ex) {
            log.debug(String.valueOf(ex));
            JOptionPane.showMessageDialog(null,
                    "Connection problem. Try again later ",
                    "HttpClient Error",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (InterruptedException ex) {
            log.debug(String.valueOf(ex));
        } catch (UserNotFoundException ex) {
            log.debug(String.valueOf(ex));
            if (JOptionPane.showConfirmDialog(null,
                    "User " + userName + " not found. Do you wish to register",
                    "Warning",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                //register user
                JOptionPane.showMessageDialog(null,
                        "User " + userName + " created. You can log in",
                        "Registration",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {

            }
        }
        return user;
    }

    public UserDto userLogOffAction(UserDto user) {
        try {
            user.setActiveStatus(false);
            user = httpBackendClient.userUpdate(user);
        } catch (IOException ex) {
            log.debug(String.valueOf(ex));
            JOptionPane.showMessageDialog(null,
                    "Connection problem. Try again later ",
                    "HttpClient Error",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (InterruptedException ex) {
            log.debug(String.valueOf(ex));
        }
        return user;
    }
}
