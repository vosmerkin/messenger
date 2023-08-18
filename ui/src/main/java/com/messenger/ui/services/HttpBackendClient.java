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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HttpBackendClient {
    private static final Logger LOG = LoggerFactory.getLogger(HttpBackendClient.class);
    public static final String APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE = "Content-Type";

    private final HttpClient client;
    private final String backendHost;
    private final String userCreateAddress;
    private final String roomCreateAddress;
    private final String userRequestAddress;
    private final String userUpdateAddress;
    private final String roomRequestAddress;
    private final String roomUpdateUsersListAddress;
    private final String messageSendAddress;
    private final String messagesByRoomIdGetAddress;
    private final String messageByIdGetAddress;


    public HttpBackendClient() {
        client = HttpClient.newHttpClient();
        backendHost = PropertyManager.getProperty("backend.host");
        LOG.info("backend.host {}", backendHost);
        userCreateAddress = backendHost + PropertyManager.getProperty("backend.user_create");
        userRequestAddress = backendHost + PropertyManager.getProperty("backend.user_request");
        userUpdateAddress = backendHost + PropertyManager.getProperty("backend.user_update");
        roomCreateAddress = backendHost + PropertyManager.getProperty("backend.room_create");
        roomRequestAddress = backendHost + PropertyManager.getProperty("backend.room_request");
        roomUpdateUsersListAddress = backendHost + PropertyManager.getProperty("backend.room_update_users");
        messageSendAddress = backendHost + PropertyManager.getProperty("backend.message_send");
        messagesByRoomIdGetAddress = backendHost + PropertyManager.getProperty("backend.messages_get");
        messageByIdGetAddress = backendHost + PropertyManager.getProperty("backend.message_get");
    }


    public UserDto userRequest(String userName) throws InterruptedException, UserNotFoundException, IOException {
        var url = userRequestAddress + userName;
        LOG.info("Requesting user {} at remote host: {}", userName, url);
        var request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        if (response.statusCode() == 404) throw new UserNotFoundException("User '" + userName + "' not found");
        return JsonMapper.fromJson(response.body(), UserDto.class);
    }

    public UserDto userUpdate(UserDto userDto) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(URI.create(userUpdateAddress))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .PUT(HttpRequest.BodyPublishers.ofString(JsonMapper.toJson(userDto)))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + userUpdateAddress);
        }
        return JsonMapper.fromJson(response.body(), UserDto.class);
    }

    public UserDto userCreate(String userName) throws InterruptedException, IOException {
        var url = userCreateAddress + userName;
        LOG.debug("Creating a new user: {}, url address: {}", userName, url);
        var request = HttpRequest.newBuilder(URI.create(url))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            LOG.error("User creation has failed: {}", e.getMessage());
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        if (response.statusCode() > 399) {
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        LOG.info("A new user has been created: {}. Server response: {}", userName, response.body());
        return JsonMapper.fromJson(response.body(), UserDto.class);
    }

    public RoomDto roomRequest(String roomName) throws IOException, InterruptedException, RoomNotFoundException {
        var url = roomRequestAddress + roomName;
        LOG.info("Requesting room {} at remote host: {}", roomName, url);
        var request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        if (response.statusCode() == 404) throw new RoomNotFoundException("Room '" + roomName + "' not found");
        return JsonMapper.fromJson(response.body(), RoomDto.class);
    }

    public RoomDto roomCreate(String roomName) throws InterruptedException, IOException {
        var url = roomCreateAddress + roomName;
        LOG.debug("Creating a new room: {}, url address: {}", roomName, url);
        var request = HttpRequest.newBuilder(URI.create(url))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        LOG.info("A new room has been created: {}. Server response: {}", roomName, response.body());
        if (response.statusCode() > 399) {
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        return JsonMapper.fromJson(response.body(), RoomDto.class);
    }

    public RoomDto roomUpdateUsersList(RoomDto currentRoom) throws InterruptedException, HttpClientIOException {
        var request = HttpRequest.newBuilder(URI.create(roomUpdateUsersListAddress))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .PUT(HttpRequest.BodyPublishers.ofString(JsonMapper.toJson(currentRoom)))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + userUpdateAddress);
        }
        return JsonMapper.fromJson(response.body(), RoomDto.class);
    }

    public void messageSend(MessageDto message) throws InterruptedException, HttpClientIOException {
        LOG.debug("HttpClient sending message {}", message.getMessageText());
        var request = HttpRequest.newBuilder(URI.create(messageSendAddress))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .PUT(HttpRequest.BodyPublishers.ofString(JsonMapper.toJson(message)))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + messageSendAddress);
        }
    }

    public List<MessageDto> getMessagesbyRoomId(Integer roomId) throws HttpClientIOException, InterruptedException {
        var url = messagesByRoomIdGetAddress + roomId;
        LOG.debug("Requesting all messages for roomId: {}, url address: {}", roomId, url);
        var request = HttpRequest.newBuilder(URI.create(url))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        if (response.statusCode() > 399) {
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        MessageDto[] messagesArray = JsonMapper.fromJson(response.body(), MessageDto[].class);
        return new ArrayList<>(Arrays.asList(messagesArray));

    }

    public MessageDto getMessageById(Integer messageId) throws IOException, InterruptedException {
        var url = messageByIdGetAddress + messageId;
        LOG.debug("Requesting message by Id: {}, url address: {}", messageId, url);
        var request = HttpRequest.newBuilder(URI.create(url))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        if (response.statusCode() > 399) {
            throw new HttpClientIOException("IOException to remote address " + url);
        }
        MessageDto message = JsonMapper.fromJson(response.body(), MessageDto.class);
        return message;
    }
}
