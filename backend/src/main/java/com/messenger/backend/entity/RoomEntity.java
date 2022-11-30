package com.messenger.backend.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tbl_room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "room_name")
    private String roomName;

    @ManyToMany
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
}
