package com.messenger.ui.services;


import com.messenger.common.dto.*;
import com.messenger.ui.exceptions.HttpClientIOException;
import com.messenger.ui.exceptions.RoomNotFoundException;
import com.messenger.ui.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class HttpBackendClient {
    private static final Logger log = LoggerFactory.getLogger(HttpBackendClient.class);

    String backendHost = PropertyManager.getProperty("backend.host");
    private HttpClient client = HttpClient.newHttpClient();
    private HttpRequest request;


    public HttpBackendClient() {
    }


    public UserDto userRequest(String userName) throws InterruptedException, UserNotFoundException, IOException {
        UserDto result;
        String resultString;
        String userRequestAddress = PropertyManager.getProperty("backend.user_request") + userName;
        log.debug("Connecting remote host {}", backendHost + userRequestAddress);
        request = HttpRequest.newBuilder(URI.create(backendHost + userRequestAddress))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + userRequestAddress);
        }
        resultString = response.body();
        if (response.statusCode() == 404) throw new UserNotFoundException("User '" + userName + "' not found");
        result = JsonMapper.fromJson(resultString, UserDto.class);
        return result;
    }


    public UserDto userUpdate(UserDto userDto) throws IOException, InterruptedException {
        UserDto result;
        String resultString;
        String userUpdateAddress = PropertyManager.getProperty("backend.user_update");
        request = HttpRequest.newBuilder(URI.create(backendHost + userUpdateAddress))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(JsonMapper.toJson(userDto)))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + userUpdateAddress);
        }
        resultString = response.body();
        result = JsonMapper.fromJson(resultString, UserDto.class);
        return result;
    }

    public UserDto userCreate(NewUserDto newUserDto) throws InterruptedException, IOException {
        UserDto userDto;
        String resultString;
        String userCreateAddress = PropertyManager.getProperty("backend.user_create");
        log.debug("Connecting remote host {}", backendHost + userCreateAddress);
        request = HttpRequest.newBuilder(URI.create(backendHost + userCreateAddress))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.toJson(newUserDto)))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + userCreateAddress);
        }
        resultString = response.body();
        userDto = JsonMapper.fromJson(resultString, UserDto.class);
        return userDto;
    }

    public RoomDto roomRequest(String roomName) throws IOException, InterruptedException, RoomNotFoundException {
        RoomDto roomDto;
        String resultString;
        String roomRequestAddress = PropertyManager.getProperty("backend.room_request") + roomName;
        request = HttpRequest.newBuilder(URI.create(backendHost + roomRequestAddress))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + roomRequestAddress);
        }
        resultString = response.body();
        if (response.statusCode() == 404) throw new RoomNotFoundException("Room '" + roomName + "' not found");
        roomDto = JsonMapper.fromJson(resultString, RoomDto.class);
        return roomDto;
    }

    public RoomDto roomCreate(NewRoomDto newRoomDto) throws InterruptedException, IOException {
        RoomDto roomDto;
        String resultString;
        String roomCreateAddress = PropertyManager.getProperty("backend.room_create");
        request = HttpRequest.newBuilder(URI.create(backendHost + roomCreateAddress))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.toJson(newRoomDto)))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + roomCreateAddress);
        }
        resultString = response.body();
        roomDto = JsonMapper.fromJson(resultString, RoomDto.class);
        return roomDto;
    }


}
