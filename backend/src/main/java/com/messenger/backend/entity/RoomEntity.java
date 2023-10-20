package com.messenger.backend.entity;

import com.messenger.common.dto.RoomDto;
import com.messenger.common.dto.UserDto;
import grpc_generated.RoomProto;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_room")
public class RoomEntity {
    public static final RoomEntity EMPTY_ENTITY = new RoomEntity(-1, "DEFAULT ROOM", Collections.emptySet());
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "room_name")
    private String roomName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_room_users",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> roomUsers;

    public RoomEntity() {
    }

    public RoomEntity(Integer id, String roomName, Set<UserEntity> roomUsers) {
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

    public Set<UserEntity> getRoomUsers() {
        return roomUsers;
    }

    public void setRoomUsers(Set<UserEntity> roomUsers) {
        this.roomUsers = roomUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return id.equals(that.id) && roomName.equals(that.roomName) && Objects.equals(roomUsers, that.roomUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomName);
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", roomUsers=" + roomUsers +
                '}';
    }

    public static RoomProto toProto(RoomEntity room) {
        if (room == null) return null;
        return RoomProto.newBuilder()
                .setRoomId(room.getId())
                .setRoomName(room.getRoomName())
                .addAllRoomUsers(new HashSet<>(room.getRoomUsers().stream().map(UserEntity::toProto).collect(Collectors.toSet())))
                .build();
//        new HashSet<>(roomProto.getRoomUsersList().stream().map(UserDto::fromProto).collect(Collectors.toSet()))
    }

    public static RoomEntity fromProto(RoomProto roomProto) {
        if (roomProto == null) return RoomEntity.EMPTY_ENTITY;
        return new RoomEntity(roomProto.getRoomId(),
                roomProto.getRoomName(),
                new HashSet<>(roomProto.getRoomUsersList().stream().map(UserEntity::fromProto).collect(Collectors.toSet())));
    }
}
