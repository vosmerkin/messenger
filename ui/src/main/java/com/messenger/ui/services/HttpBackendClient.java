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
    public static final String APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE = "Content-Type";

    private final HttpClient client;
    private final String backendHost;
    private final String userCreateAddress;
    private final String roomCreateAddress;
    private final String userRequestAddress;
    private final String userUpdateAddress;
    private final String roomRequestAddress;


    public HttpBackendClient() {
        client = HttpClient.newHttpClient();
        backendHost = PropertyManager.getProperty("backend.host");
        userCreateAddress = backendHost + PropertyManager.getProperty("backend.user_create");
        userRequestAddress = backendHost + PropertyManager.getProperty("backend.user_request");
        userUpdateAddress = backendHost + PropertyManager.getProperty("backend.user_update");
        roomCreateAddress = backendHost + PropertyManager.getProperty("backend.room_create");
        roomRequestAddress = backendHost + PropertyManager.getProperty("backend.room_request");
    }


    public UserDto userRequest(String userName) throws InterruptedException, UserNotFoundException, IOException {
        var url = userRequestAddress + userName;
        log.info("Requesting user {} at remote host: {}", userName, url);
        var request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + userRequestAddress);
        }

        if (response.statusCode() == 404) throw new UserNotFoundException("User '" + userName + "' not found");

        return JsonMapper.fromJson(response.body(), UserDto.class);
    }

    public UserDto userUpdate(UserDto userDto) throws IOException, InterruptedException {
        UserDto result;
        String resultString;
        var request = HttpRequest.newBuilder(URI.create(userUpdateAddress))
                .header(CONTENT_TYPE, APPLICATION_JSON)
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

    public UserDto userCreate(String userName) throws InterruptedException, IOException {
        var url = userCreateAddress + userName;
        log.debug("Creating a new user: {}, url address: {}", userName, url);
        var request = HttpRequest.newBuilder(URI.create(url))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            log.error("User creation has failed: {}", e.getMessage());
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        if (response.statusCode() > 399) {
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        log.info("A new user has been created: {}. Server response: {}", userName, response.body());
        return JsonMapper.fromJson(response.body(), UserDto.class);
    }

    public RoomDto roomRequest(String roomName) throws IOException, InterruptedException, RoomNotFoundException {
        RoomDto roomDto;
        String resultString;
        var request = HttpRequest.newBuilder(URI.create(roomRequestAddress + roomName))
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
        var request = HttpRequest.newBuilder(URI.create(roomCreateAddress))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.toJson(newRoomDto)))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + roomCreateAddress);
        }
        log.info("A new room has been created: {}. Server response: {}", newRoomDto, response.body());
        if (response.statusCode() > 399) {
            throw new HttpClientIOException("IOException to remote address " + roomCreateAddress);
        }

        return JsonMapper.fromJson(response.body(), RoomDto.class);
    }

}
