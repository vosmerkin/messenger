package com.messenger.ui.services;


import com.messenger.common.dto.JsonMapper;
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


    private HttpClient client = HttpClient.newHttpClient();
    private HttpRequest request;


    public HttpBackendClient() {
    }


    public UserDto userRequest(String name) throws IOException, InterruptedException, UserNotFoundException {
        UserDto result;
        String resultString;
        request = HttpRequest.newBuilder(URI.create(Addresses.USER_REQUEST + name))
                .GET()
                .build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        resultString = response.body();
        if (response.statusCode() == 404) throw new UserNotFoundException("User '" + name + "' not found");
        result = JsonMapper.fromJson(resultString, UserDto.class);
        return result;
    }


    public UserDto userUpdate(UserDto data) throws IOException, InterruptedException {
        UserDto result;
        String resultString;
        request = HttpRequest.newBuilder(URI.create(Addresses.UPDATE))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(JsonMapper.toJson(data)))
                .build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        resultString = response.body();
        result = JsonMapper.fromJson(resultString, UserDto.class);
        return result;
    }

    public RoomDto roomRequest(String roomName) throws IOException, InterruptedException, RoomNotFoundException {
        RoomDto roomDto;
        String resultString;
        request = HttpRequest.newBuilder(URI.create(Addresses.ROOM_REQUEST + roomName))
                .GET()
                .build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        resultString = response.body();
        if (response.statusCode() == 404) throw new RoomNotFoundException("Room '" + roomName + "' not found");
        roomDto = JsonMapper.fromJson(resultString, RoomDto.class);
        return roomDto;

    }
}
