package com.messenger.backend.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "users", columnDefinition = "json")
    @Convert(converter = UserListToStringConverter.class)
    private List<UserEntity> roomUsers;


}
