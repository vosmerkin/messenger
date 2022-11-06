package com.messenger.common.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RoomDto implements Serializable {
    @Null(groups = {RoomDto.NewRoom.class})
    private final Integer id;
    @NotNull(groups = {RoomDto.NewRoom.class})
    private final String roomName;
    @Null(groups = {RoomDto.NewRoom.class})
    private Set<UserDto> roomUsers;

    public RoomDto(@Null(groups = {NewRoom.class}) Integer id, @NotNull(groups = {NewRoom.class}) String roomName) {
        this.id = id;
        this.roomName = roomName;
    }
    public interface NewRoom{
    }

    public Integer getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public Set<UserDto> getRoomUsers() {
        Set<UserDto> users = new HashSet<>();
        users.addAll(roomUsers);
        return users;
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
