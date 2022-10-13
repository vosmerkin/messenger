package com.messenger.common.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class RoomDto implements Serializable {
    private final Integer id;
    private final String roomName;
    private Set<UserDto> roomUsers;

    public RoomDto(Integer id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public Integer getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public Set<UserDto> getRoomUsers() {
        return roomUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto entity = (RoomDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.roomName, entity.roomName) &&
                Objects.equals(this.roomUsers, entity.roomUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomName, roomUsers);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "roomName = " + roomName + ", " +
                "roomUsers = " + roomUsers + ")";
    }
}
