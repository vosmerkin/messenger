package com.messenger.common.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class RoomDto implements Serializable {

    private Integer id;
    private String roomName;

    private Set<UserDto> roomUsers;

    public RoomDto() {
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
                ", roomUsers=" + roomUsers +
                '}';
    }

}
