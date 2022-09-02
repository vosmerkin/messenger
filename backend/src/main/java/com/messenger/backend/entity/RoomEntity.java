package com.messenger.backend.entity;

import com.messenger.backend.services.UserListToStringConverter;

import javax.persistence.*;
import java.util.List;
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




}
