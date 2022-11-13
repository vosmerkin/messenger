package com.messenger.ui.services;


import com.messenger.common.dto.JsonMapper;
import com.messenger.common.dto.NewRoomDto;
import com.messenger.common.dto.RoomDto;
import com.messenger.common.dto.UserDto;
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
        request = HttpRequest.newBuilder(URI.create(backendHost + userRequestAddress))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new IOException("IOException to remote address " + userRequestAddress);
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
            throw new IOException("IOException to remote address " + userUpdateAddress);
        }
        resultString = response.body();
        result = JsonMapper.fromJson(resultString, UserDto.class);
        return result;
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
            throw new IOException("IOException to remote address " + roomRequestAddress);
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
            throw new IOException("IOException to remote address " + roomCreateAddress);
        }
        resultString = response.body();
        roomDto = JsonMapper.fromJson(resultString, RoomDto.class);
        return roomDto;
    }

}
