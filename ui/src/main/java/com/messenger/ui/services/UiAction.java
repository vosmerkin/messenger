package com.messenger.ui.services;

import com.messenger.common.dto.RoomDto;
import com.messenger.common.dto.UserDto;
import com.messenger.ui.exceptions.RoomNotFoundException;
import com.messenger.ui.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;

public class UiAction {
    private static final Logger log = LoggerFactory.getLogger(HttpBackendClient.class);
    private HttpBackendClient httpBackendClient = new HttpBackendClient();

    public UserDto userLogInAction(String userName) {
        UserDto userDto = null;
        try {
            userDto = httpBackendClient.userRequest(userName);
            userDto.setActiveStatus(true);
//            userDto = httpBackendClient.userUpdate(userDto);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Connection problem. Try again later ",
                    "HttpClient Error",
                    JOptionPane.INFORMATION_MESSAGE);
            log.debug("IOException to remote address {}", ex);
        } catch (InterruptedException ex) {
            log.debug("InterruptedException {}", ex);
        } catch (UserNotFoundException ex) {
            log.debug("UserNotFoundException. Username - {}. {}", userName, ex);
            if (JOptionPane.showConfirmDialog(null,
                    "User " + userName + " not found. Do you wish to register",
                    "Warning",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                //register user
                userDto = createUser(userName);
                JOptionPane.showMessageDialog(null,
                        "User " + userName + " created. You can log in",
                        "Registration",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return userDto;
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

    private UserDto createUser(String userName) {
        UserDto userDto = null;
        try {
            log.info("Creating a new user: {}", userName);
            userDto = httpBackendClient.userCreate(userName);
        } catch (IOException ex) {
            log.debug("IOException to remote address {}", ex);
            JOptionPane.showMessageDialog(null,
                    "Connection problem. Try again later ",
                    "HttpClient Error",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (InterruptedException ex) {
            log.debug("InterruptedException {}", ex);
        }
        return userDto;
    }

    public RoomDto roomEnter(String roomName) {
        RoomDto roomDto = null;
        try {
            //get roomDto
            roomDto = httpBackendClient.roomRequest(roomName);
        } catch (IOException ex) {
            log.debug("IOException to remote address {}", ex);
            JOptionPane.showMessageDialog(null,
                    "Connection problem. Try again later ",
                    "HttpClient Error",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (InterruptedException ex) {
            log.debug("InterruptedException {}", ex);
        } catch (RoomNotFoundException ex) {
            log.debug("RoomNotFoundException. Roomname - {}. {}", roomName, ex);
            if (JOptionPane.showConfirmDialog(null,
                    "Room " + roomName + " not found. Do you wish to create?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                //if not present - create room
                roomDto = createRoom(roomName);
                JOptionPane.showMessageDialog(null,
                        "Room " + roomName + " created. You can now enter",
                        "Room created",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

        //if present - get room users and history
        return roomDto;
    }

    private RoomDto createRoom(String roomName) {
        RoomDto roomDto = null;
        try {
            //get roomDto
            roomDto = httpBackendClient.roomCreate(roomName);
        } catch (IOException ex) {
            log.debug("IOException to remote address {}", ex);
            JOptionPane.showMessageDialog(null,
                    "Connection problem. Try again later ",
                    "HttpClient Error",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (InterruptedException ex) {
            log.debug("InterruptedException {}", ex);
        }
        return roomDto;
    }

    public RoomDto leaveRoom(RoomDto currentRoom) {
        try {
            currentRoom = httpBackendClient.roomUpdateUsersList(currentRoom);
        } catch (IOException ex) {
            log.debug(String.valueOf(ex));
            JOptionPane.showMessageDialog(null,
                    "Connection problem. Try again later ",
                    "HttpClient Error",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (InterruptedException ex) {
            log.debug(String.valueOf(ex));
        }
        return currentRoom;
    }
}
