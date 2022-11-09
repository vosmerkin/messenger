package com.messenger.common.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class NewRoomDto implements Serializable {
    private Integer id;
    @NotNull
    private final String roomName;
    private Set<UserDto> roomUsers;

    public NewRoomDto( String roomName) {
        this.roomName = roomName;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewRoomDto entity = (NewRoomDto) o;
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
