package com.messenger.common.dto;

import java.io.Serializable;
import java.util.*;

public class RoomDto implements Serializable {

    public static final RoomDto EMPTY_ENTITY = new RoomDto(-1, "DEFAULT ROOM", Collections.emptySet());

    private Integer id;
    private String roomName;

    private Set<UserDto> roomUsers;

    public RoomDto() {
    }

    public RoomDto(Integer id, String roomName, Set<UserDto> roomUsers) {
        this.id = id;
        this.roomName = roomName;
        this.roomUsers = roomUsers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Set<UserDto> getRoomUsers() {
        return roomUsers;
    }

    public void setRoomUsers(Set<UserDto> roomUsers) {
        this.roomUsers = roomUsers;
    }

    public void addRoomUser(UserDto user) {
        roomUsers.add(user);
    }

    public void removeRoomUser(UserDto user) {
        if (roomUsers.contains(user)) roomUsers.remove(user);
    }

    public Set<String> getRoomUserNames(){
        Set<String> set = new HashSet<>();
            if (roomUsers.size()>0)
                for (UserDto user:roomUsers) set.add(user.getUserName());
        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto roomDto = (RoomDto) o;
        return Objects.equals(id, roomDto.id) && Objects.equals(roomName, roomDto.roomName) && Objects.equals(roomUsers, roomDto.roomUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomName, roomUsers);
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                '}';
    }

}
